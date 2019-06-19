package eu.cise.emulator.app.cli;
import eu.cise.emulator.app.CiseEmuApplication;
import io.dropwizard.websockets.GeneralUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.TimeUnit.SECONDS;


public class CommandLineSenderTest   {

    private CommandLineSender mycommand;
    private CiseEmuApplication myapp;
    private Thread serverThread ;
    private Thread CliThread;

    @Before
    public void setup() throws InterruptedException {
        // Before each test, we re-instantiate our resource so it will reset
        // the counter. It is good practice when dealing with a class that
        // contains mutable data to reset it so tests can be ran independently
        // of each other.
        mycommand = new CommandLineSender();
        CiseEmuApplication AppLiveRunning;
        CountDownLatch serverStarted = new CountDownLatch(1);
        serverThread = new Thread(GeneralUtils.rethrow(() -> new CiseEmuApplication(serverStarted)
                    .run("server","config.yml"))); // was fixed argument server config.yml (root of the directory jar -> class)
        serverThread.setDaemon(true);
        serverThread.start();

            serverStarted.await(3, SECONDS);

        }


    @Test
    public void idStartsAtOne() throws InterruptedException {
        CountDownLatch serverStarted = new CountDownLatch(2);
        CliThread = new Thread(GeneralUtils.rethrow(() -> new CiseEmuApplication(serverStarted)
                .run("sender","clisource/AisModifiedPush.xml"))); // was fixed argument server config.yml (root of the directory jar -> class)
        serverStarted.await(5, SECONDS);
    }
    @After
    public void teardown() throws InterruptedException {

        serverThread.interrupt();
        CliThread.interrupt();
    }




}
