package eu.europa.ec.jrc.marex.cli


import eu.europa.ec.jrc.marex.CiseEmulatorApplication
import eu.europa.ec.jrc.marex.CiseEmulatorConfiguration
import io.dropwizard.cli.Cli
import io.dropwizard.setup.Bootstrap
import io.dropwizard.util.JarLocation
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import spock.lang.Shared
import spock.lang.Specification

import java.nio.channels.FileChannel
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


class ServerCustomCommandTest extends Specification {


    public final PrintStream originalOut = System.out;

    public final PrintStream originalErr = System.err;

    public final InputStream originalIn = System.in;

    public final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();

    public final ByteArrayOutputStream stdErr = new ByteArrayOutputStream();
    public JarLocation jarLocation;
    public Path destpath, srcPath;
    public File configfile
    public Cli cli;

    @Before
    public void simplesetup() throws Exception {
        // Setup necessary mock
        final URL location = CiseEmulatorApplication.class.getClassLoader().getResource(".");
        // create file copy for test
        srcPath = Paths.get(new URI(location.toString() + "config.yml"));
        destpath = Files.createTempFile("config", ".yml");

        Path copiedFile = Files.copy(
                srcPath,
                this.destpath,
                StandardCopyOption.REPLACE_EXISTING
        );
        // create a mock object for jar execution
        jarLocation = new JarLocation(getClass());
        // Redirect stdout and stderr to our byte streams
        System.setOut(new PrintStream(stdOut));
        System.setErr(new PrintStream(stdErr));
    }


    @Test
    def " standard 'server' invocation of dropwizard using absolute filepath as first param"() {
        given: "exec directory is provided with configuration file"
        Bootstrap<CiseEmulatorConfiguration> bootstrap = new Bootstrap<>(new CiseEmulatorApplication());
        cli = new Cli(jarLocation, bootstrap, stdOut, stdErr);
        when: "parameter is simple server with valid relative path"
        final boolean success = cli.run("server", destpath.toString());
        then: " response is ok  AND a file have been created in the output directory"
        this.destpath.getBytes() == this.srcPath.getBytes()
    }

    @After
    public void simpleteardown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
        destpath.getParent().deleteDir();
    }

}