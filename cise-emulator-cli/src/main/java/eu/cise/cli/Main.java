package eu.cise.cli;

public class Main implements Runnable {

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void run() {
        CliAppContext cliAppContext = new CliAppContext();

        UseCaseSendMessage useCaseSendMessage = new UseCaseSendMessage(cliAppContext.makeEmulatorEngine());
        
        useCaseSendMessage.send();
    }
}
