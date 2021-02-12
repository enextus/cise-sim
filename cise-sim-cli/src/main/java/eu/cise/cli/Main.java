/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.cli;

import com.beust.jcommander.JCommander;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.sim.engine.SendParam;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static spark.Spark.port;
import static spark.Spark.post;

public class Main implements Runnable {

    private static Args args;

    public static void main(String[] argv) {
        args = new Args();
        Main main = new Main();
        var jcmd = JCommander.newBuilder()
                .addObject(args)
                .build();
        jcmd.parse(argv);

        if (args.help) {
            jcmd.usage();
            System.exit(0);
        }

        main.run();
    }

    @Override
    public void run() {
        System.setProperty("conf.dir", args.config);

        var appContext = new CliAppContext();

        var useCaseSendMessage = new UseCaseSendMessage(
                null, // appContext.makeSimEngine(),
                appContext.makeMessageLoader(),
                appContext.makeMessageProcessor());

        var useCaseReciveMessage = new UseCaseReciveMessage(
                appContext.makeSimEngine(), appContext.makeMessageLoader()
        );


        if (args.listen) {
            port(args.port);
            post("/", (request, response) -> {
                var xmlMapper = appContext.getXmlMapper();

                Acknowledgement ack = useCaseReciveMessage.receive(xmlMapper.fromXML(request.body()));

                response.status(201);
                response.type("application/xml");

                return xmlMapper.toXML(ack);
            });
        } else {
            if (args.sincN > 0) {

                sendMultiSinc(args.sincN, useCaseSendMessage, args.filename, args.requiresAck, args.correlationId);

            } else if (args.asincN > 0) {

                sendMultiASinc(args.asincN, useCaseSendMessage, args.filename, args.requiresAck, args.correlationId);

            } else {

                sendMessage(useCaseSendMessage, args.filename, args.requiresAck, args.messageId, args.correlationId);
            }
        }

    }

    private void sendMultiSinc(int n, UseCaseSendMessage useCaseSendMessage, String filename, boolean requiresAck, String correlationId) {

        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        for (int i = 0; i < n; ++i) {
            String messageId = UUID.randomUUID().toString();
            sendMessage(useCaseSendMessage, filename, requiresAck, messageId, correlationId);
        }
    }

    private void sendMultiASinc(int n, UseCaseSendMessage useCaseSendMessage, String filename, boolean requiresAck, String correlationId) {

        ExecutorService executor = Executors.newCachedThreadPool();

        final String correlationIdNew = StringUtils.isEmpty(correlationId) ? UUID.randomUUID().toString() : correlationId;

        for (int i = 0; i < n; ++i) {
            String messageId = UUID.randomUUID().toString();
            executor.execute(() -> sendMessage(useCaseSendMessage, filename, requiresAck, messageId, correlationIdNew));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }


    private void sendMessage(UseCaseSendMessage useCaseSendMessage, String filename, boolean requiresAck, String messageId, String correlationId) {
        var sendParam = new SendParam(requiresAck, messageId, correlationId);
        useCaseSendMessage.send(filename, sendParam);
    }
}
