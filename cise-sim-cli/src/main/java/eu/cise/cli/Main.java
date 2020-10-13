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
