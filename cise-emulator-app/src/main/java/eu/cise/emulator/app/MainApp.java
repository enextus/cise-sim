/*
 * Copyright CISE AIS Adaptor (c) 2018, European Union
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package eu.cise.emulator.app;

import eu.cise.emulator.app.candidate.Sender;
import eu.cise.emulator.app.candidate.SourceStreamProcessor;
import eu.cise.emulator.app.context.DefaultAppContext;
import eu.cise.emulator.app.context.SimConfig;
import eu.cise.emulator.app.util.SimLogger;
import eu.cise.emulator.integration.Validation.MessageValidator;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;

/**
 * The MainApp class is the application entry point. It accepts the
 * -d parameter to be more verbose when reporting errors.
 */
public class MainApp implements Runnable {

    private final Banner banner;
    private final AppContext ctx;
    private final SimApp simApp;

    public MainApp(SimConfig config) {
        ctx = new DefaultAppContext(config);
        banner = new Banner();
        XmlMapper xmlMapper = new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        simApp = new SimApp(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                config, xmlMapper, validator);
    }

    /**
     * Application starts here
     *
     * @param args possible application parameters. The only one accepted is -d
     */
    public static void main(String[] args) {
        try {

            new MainApp(createConfig()).run();

        } catch (Throwable e) {
            System.err.println("An error occurred:\n\n" + e.getMessage() + "\n");

            if (optionDebug(args))
                e.printStackTrace();
        }
    }

    /**
     * Retrieve the configuration object.
     *
     * @return a SimConfig object.
     */
    private static SimConfig createConfig() {
        return ConfigFactory.create(SimConfig.class);
    }

    /**
     * Support extended '--debug' and brief '-d' format
     *
     * @param args the argument
     * @return true or false if the debug is enabled or not.
     */
    private static boolean optionDebug(String[] args) {
        return args.length > 0 && (args[0].equals("--debug") || args[0].equals("-d"));
    }

    /**
     * The when invoked, display a banner, dump the configuration and
     * starts the application.
     */
    @Override
    public void run() {
        banner.print();
        simApp.run();
    }

}