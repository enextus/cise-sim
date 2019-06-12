package eu.cise.emulator.httptransport;

import eu.cise.emulator.httptransport.conformance.Consumers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimAppConsumers {


    public static Logger logger = LoggerFactory.getLogger(SimAppConsumers.class);

    private Consumers consumers;

    public static void main(String[] args) {
        new SimAppConsumers().start();
    }

    public void start() {

        logger.info("Starting Gateway Consumers");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Shutdown hook ran!")));

     consumers=null;
    }


    public void stop() {
        if (consumers != null) {
            consumers.shutDown();
        }
    }
}
