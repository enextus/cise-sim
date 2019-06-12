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
import eu.cise.emulator.httptransport.Validation.MessageValidator;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The MainApp class is the application entry point. It accepts the
 * -d parameter to be more verbose when reporting errors.
 */
public class MainCLIApp implements Runnable {

    private static  MainCLIApp sMCLIApp = null;
    private final Banner banner;
    private final AppContext ctx;
    private final SimApp simApp;

    public MainCLIApp(SimConfig config) {
        ctx = new DefaultAppContext(config);
        banner = new Banner();
        XmlMapper xmlMapper= new DefaultXmlMapper();
        MessageValidator validator = new MessageValidator();
        simApp = new SimApp(new SourceStreamProcessor(),
                new Sender(),
                new SimLogger.Slf4j(),
                config, xmlMapper, validator);
    }

    /**
     * Application starts here
     *
     * @param args possible application parameters. accepted are
     *
     *
     *             ie :java -jar ./sim-app.jar -d -v -s template.xml -p vessel.xml
     *  -d,--debug     Turn on debug.
     *  -h,--help      display the help
     *  -p <arg>       payload
     *  -s <arg>       service
     *  -v,--verbose   Turn on verbose.
     */
    public static void main(String[] args) {
        try {

            sMCLIApp= new MainCLIApp(createConfig());

            // create Options object
            final Options options = new Options();

            // add t option
            options.addOption("h","help", false, "display the help");
            options.addOption(new Option("d", "debug", false, "Turn on debug."));
            options.addOption(new Option("v", "verbose", false, "Turn on verbose."));

            options.addOption("s", true, "service");
            options.addOption("p", true, "payload");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd=null;
            HelpFormatter formatter = new HelpFormatter();
            try {
                // parse the command line arguments
                cmd = parser.parse( options, args );

            }
            catch( ParseException exp ) {
                // oops, something went wrong
                System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
                    formatter.printHelp( "help", options );
            }


            if(cmd.hasOption("h")) {
                formatter.printHelp( "help", options );
            }

            if(cmd.hasOption("d")) {
                sMCLIApp.simApp.isDebug=true;
            }

            if(cmd.hasOption("v")) {

                sMCLIApp.simApp.isVerbose=true;

            }


            Logger logger = LoggerFactory.getLogger("eu.cise.sim.app.MainCliApp_Static");
            if (sMCLIApp.simApp.isDebug=true) logger.debug(" Start sim app with parameters : " + ((cmd.hasOption("d")?" d (debug) | ":"") +(cmd.hasOption("v")?" v (verbose)  | ":"")+(cmd.hasOption("s")?"s ( service ) = "+sMCLIApp.simApp.serviceFile +" | ":"")+(cmd.hasOption("p")?"p ( payload  ) =  "+sMCLIApp.simApp.payloadFile +"\n":"")));

            sMCLIApp.run();

            /** specific to POC invocation of the method within app run  */
            String servicefile="",payloadfile="";

            if(cmd.hasOption("s")) {
                servicefile=cmd.getOptionValue("s");
                if(cmd.hasOption("p")) {
                    payloadfile=cmd.getOptionValue("p");
                }
                else {System.out.println("ERROR : whenever service defined by -s ; must also define payload with -p parameter !!! ");}
            }
            if((cmd.hasOption("p")) && !(cmd.hasOption("s"))  ) { System.out.println("ERROR : whenever payload defined by -p ; must also service with -s parameter !!!");}
                     sMCLIApp.simApp.sendEvent(servicefile,payloadfile);
        } catch (Throwable e) {
            System.err.println("An error occurred:\n\n" + e.getMessage() + "\n");

            if (sMCLIApp.simApp.isDebug=true)
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


    @Override
    public void run() {
        banner.print();
        simApp.run();

    }

}
