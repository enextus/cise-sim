package eu.cise.emulator;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The MainApp class is the application entry point. It accepts the
 * -d parameter to be more verbose when reporting errors.
 */
public class MainApp implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);
    private CiseEmulatorApp ciseEmulatorApp;

    public MainApp() {
        EmuConfig emuConfig = ConfigFactory.create(EmuConfig.class);
        AppContext appContext = new DefaultAppContext();
        MessageProcessor messageProcessor = appContext.makeMessageProcessor();
        if (System.getProperty("conf.dir") != null) {

            LOGGER.warn("conf.dir set to {} at startup, using {} endpoint", System.getProperty("conf.dir"), emuConfig.endpointUrl());
        }
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
