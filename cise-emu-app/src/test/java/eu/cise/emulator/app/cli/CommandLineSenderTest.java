package eu.cise.emulator.app.cli;
import eu.cise.emulator.app.CiseEmuApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;


public class CommandLineSenderTest   {

    private CommandLineSender mycommand;
    private CiseEmuApplication myapp;
    private Thread serverThread ;
    private Thread CliThread;

    @Before
    public void setup() throws InterruptedException {
        mycommand = new CommandLineSender();
        CiseEmuApplication AppLiveRunning;
        CountDownLatch serverStarted = new CountDownLatch(1);
        }


    @Test
    public void idStartsAtOne() throws InterruptedException {
        CountDownLatch serverStarted = new CountDownLatch(2);
    }



    @After
    public void teardown() throws InterruptedException {
    }




}
