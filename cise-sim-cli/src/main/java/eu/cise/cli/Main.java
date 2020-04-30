package eu.cise.cli;

import com.beust.jcommander.JCommander;
import eu.cise.servicemodel.v1.message.Acknowledgement;
import eu.cise.sim.engine.SendParam;

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
                appContext.makeSimEngine(), appContext.makeMessageLoader()
        );

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
            var sendParam = new SendParam(args.requiresAck, args.messageId, args.correlationId);

            useCaseSendMessage.send(args.filename, sendParam);
        }


    }
}
