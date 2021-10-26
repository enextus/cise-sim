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

package eu.cise.sim.config;

import eu.cise.sim.engine.DispatcherType;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Preprocessor;

/**
 * This file is containing the sim application configuration
 */
@Config.HotReload(type = Config.HotReloadType.SYNC)
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.PreprocessorClasses({SimConfig.TrimAndInsureBoolean.class})
@Sources({"file:${conf.dir}/sim.properties",
        "system:properties"})
public interface SimConfig extends Config {

    @Key("simulator.name")
    String simulatorName();

    @Key("destination.protocol")
    @DefaultValue("REST")
    DispatcherType destinationProtocol();

    @Key("destination.url")
    String destinationUrl();

    @Key("templates.messages.directory")
    String messageTemplateDir();

    @Key("history.repository.directory")
    String messageHistoryDir();

    @Key("history.gui.numthreads")
    int guiMaxThMsgs();

    @Key("signature.keystore.filename")
    String keyStoreFileName();

    @Key("signature.keystore.password")
    String keyStorePassword();

    @Key("signature.privatekey.alias")
    String privateKeyAlias();

    @Key("signature.privatekey.password")
    String privateKeyPassword();

    @Key("proxy.host")
    String proxyHost();

    @Key("proxy.port")
    String proxyPort();

    @Key("gui.show.incident")
    @DefaultValue("false")
    boolean showIncident();

    @Key("discovery.sender.serviceid")
    String discoverySender();

    @Key("discovery.sender.servicetype")
    String discoveryServiceType();

    @Key("discovery.sender.serviceoperation")
    String discoveryServiceOperation();

    class TrimAndInsureBoolean implements Preprocessor {
        public String process(String input) {
            if (input.trim().toUpperCase().equals("TRUE")) {
                return "true";
            } else if (input.trim().toUpperCase().equals("FALSE")) {
                return "false";
            } else {
                return input.trim();
            }
        }
    }
}
