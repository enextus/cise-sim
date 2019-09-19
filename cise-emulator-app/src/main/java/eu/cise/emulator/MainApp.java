package eu.cise.emulator;

import org.aeonbits.owner.ConfigFactory;

/**
 * The MainApp class is the application entry point. It accepts the
 * -d parameter to be more verbose when reporting errors.
 */
public class MainApp implements Runnable {

    private CiseEmulatorApp ciseEmulatorApp;

    public MainApp() {
        EmuConfig emuConfig = ConfigFactory.create(EmuConfig.class);
        AppContext appContext = new DefaultAppContext();
        MessageProcessor messageProcessor = appContext.makeMessageProcessor();

        this.ciseEmulatorApp = new CiseEmulatorApp(
                emuConfig,
                messageProcessor,
                appContext.makeEmulatorEngine(),
                appContext.makeDispatcher(),
                appContext.makeSignatureService(),
                appContext.makeEmulatorApi(messageProcessor));

    }

    @Override
    public void run() {
        ciseEmulatorApp.run();
    }

    /**
     * Application starts here
     *
     * @param args possible application parameters.
     */
    public static void main(String[] args) {
        try {
            //CiseEmulatorExtConfig config = ConfigFactory.create(CiseEmulatorExtConfig.class);

            new MainApp(/*config*/).run();

        } catch (Throwable e) {
            System.err.println("An error occurred:\n\n" + e.getMessage() + "\n");

            if (optionDebug(args))
                e.printStackTrace();
        }
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

}
