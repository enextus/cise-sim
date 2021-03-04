/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.cise.cli;

import com.beust.jcommander.Parameter;

import java.util.UUID;

public class Args {
    @Parameter(names = {"--config", "-c"}, description = "sim.property file path to configure the simulator")
    public String config = ".";

    @Parameter(names = {"--file", "-f"}, description = "File of the CISE message to be sent")
    public String filename;

    @Parameter(names = {"--listen", "-l"}, description = "Set this argument to receive messages. The default port is 9999. To specify the port use the -p option.")
    public boolean listen = false;

    @Parameter(names = {"--port", "-p"}, description = "File of the CISE message to be sent")
    public Integer port = 9999;

    @Parameter(names = {"--requires-ack", "-a"}, description = "Set to true the requiredAck field in the XML of the message")
    public boolean requiresAck = false;

    @Parameter(names = {"--message-id", "-i"}, description = "Overrides the message id of the message to be sent")
    public String messageId = UUID.randomUUID().toString();

    @Parameter(names = {"--correlation-id", "-r"}, description = "Overrides the correlation id of the message to be sent")
    public String correlationId;

    @Parameter(names = {"--sync", "-sy"}, description = "Send in a synchronously mode the indicate number of messages")
    public Integer syncN = 0;

    @Parameter(names = {"--async", "-asy"}, description = "Send in a asynchronously mode the indicate number of messages")
    public Integer asyncN = 0;

    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    public Boolean verbose = false;

    @Parameter(names = {"--debug", "-d"}, description = "Debug mode")
    public boolean debug = false;

    @Parameter(names = {"--help", "-h"}, help = true)
    public boolean help;
}