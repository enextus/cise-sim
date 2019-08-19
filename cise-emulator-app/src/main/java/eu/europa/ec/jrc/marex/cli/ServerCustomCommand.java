package eu.europa.ec.jrc.marex.cli;

import io.dropwizard.cli.Cli;
import io.dropwizard.cli.Command;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.util.JarLocation;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

public class ServerCustomCommand extends Command {

    public ServerCustomCommand() {
        super("cliserver", "customized server with functional console output");
    }

    @Override
    public void configure(Subparser subparser) {

        subparser.addArgument("-c", "--config")
                .dest("config")
                .type(String.class)
                .required(false)
                .help("add config path in order to send configured destination ");

        subparser.addArgument("-o", "--outputDirectory")
                .dest("outputDirectory")
                .type(String.class)
                .required(false)
                .help("add directory output path to save the received content  ");
    }

    @Override
    public void run(Bootstrap<?> bootstrap, Namespace namespace) throws Exception {
        final Cli cli = new Cli(new JarLocation(getClass()), bootstrap, System.out, System.err);
        /* TODO overwrite with parameter ?
        String tempFileName="emulator"+ "_" + System.currentTimeMillis();
        File tmpConfigFile = new File(
                System.getProperty("java.io.tmpdir"),tempFileName
                );
        tmpConfigFile.deleteOnExit();
        bootstrap.getObjectMapper().writeValue(tmpConfigFile);
        */
        String[] param = {"server", "cliconfig.yml"};
        cli.run(param); //(bootstrap.getApplication(),param);
    }

}