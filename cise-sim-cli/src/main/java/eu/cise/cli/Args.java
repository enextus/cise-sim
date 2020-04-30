package eu.cise.cli;

import com.beust.jcommander.Parameter;

import java.util.UUID;

public class Args {
    @Parameter(names = {"--config", "-c"}, description = "sim.property file path to configure the simulator")
    public String config = ".";

    @Parameter(names = {"--file", "-f"}, description = "File of the CISE message to be sent")
    public String filename;

    @Parameter(names = {"--listen", "-l"}, description = "Set this argument to receive messages. The default port is 9999. To specify the port use the -p option.")
    public Boolean listen = false;

    @Parameter(names = {"--port", "-p"}, description = "File of the CISE message to be sent")
    public Integer port = 9999;

    @Parameter(names = {"--requires-ack", "-a"}, description = "Set to true the requiredAck field in the XML of the message")
    public boolean requiresAck = false;

    @Parameter(names = {"--message-id", "-i"}, description = "Overrides the message id of the message to be sent")
    public String messageId = UUID.randomUUID().toString();

    @Parameter(names = {"--correlation-id", "-r"}, description = "Overrides the correlation id of the message to be sent")
    public String correlationId;

    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    public Boolean verbose = false;

    @Parameter(names = {"--debug", "-d"}, description = "Debug mode")
    public boolean debug = false;

    @Parameter(names = {"--help", "-h"}, help = true)
    public boolean help;
}