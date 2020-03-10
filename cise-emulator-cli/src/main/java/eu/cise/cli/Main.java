package eu.cise.cli;

import com.beust.jcommander.JCommander;
import eu.cise.emulator.SendParam;

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
                appContext.makeEmulatorEngine(), appContext.makeMessageLoader()
        );

        var sendParam = new SendParam(args.requiresAck, args.messageId, args.correlationId);

        useCaseSendMessage.send(args.filename, sendParam);
    }
}
