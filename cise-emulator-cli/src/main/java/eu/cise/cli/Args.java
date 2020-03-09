package eu.cise.cli;

import com.beust.jcommander.Parameter;

import java.util.UUID;

public class Args {
    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    public Boolean verbose = false;

    @Parameter(names = {"--file", "-f"}, required = true, description = "File of the CISE message to be sent")
    public String filename;

    @Parameter(names = {"--config", "-c"}, required = true, description = "sim.property file path to configure the simulator")
    public String config;

    @Parameter(names = {"--requires-ack", "-r"}, description = "Debug mode")
    public boolean requiresAck = false;

    @Parameter(names = {"--message-id", "-i"}, description = "Overrides the message id of the message to be sent")
    public String messageId = UUID.randomUUID().toString();

    @Parameter(names = {"--correlation-id", "-l"}, description = "Overrides the correlation id of the message to be sent")
    public String correlationId;

    @Parameter(names = {"--debug", "-d"}, description = "Debug mode")
    public boolean debug = false;

    @Parameter(names = {"--help", "-h"}, help = true)
    public boolean help;
}