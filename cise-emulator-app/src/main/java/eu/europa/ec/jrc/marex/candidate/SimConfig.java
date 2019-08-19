/*
 * Copyright CISE AIS Adaptor (c) 2018, European Union
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package eu.europa.ec.jrc.marex.candidate;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

/**
 * Extending the {AdaptorConfig} configuration object adding properties
 * specific to the Certificate and Signature
 */
@Sources({"file:${conf.dir}simapp.properties",
        "classpath:simapp.properties"})
public interface SimConfig extends Config {

    //SIM running mode; values: LSA | EGN
    @DefaultValue("LSA")
    @Key("simulator.mode")
    String getSimulatorMode();

    //SIM web service mode; values: SOAP | REST
    @DefaultValue("REST")
    @Key("webapp.ws.mode")
    String getWebappWsMode();

    @DefaultValue("http://localhost:8080/sim-LSA/soap/CISEMessageService")
    @Key("counterpart.soap.url")
    String getCounterpartSoapUrl();

    @DefaultValue("http://localhost:8080/sim-LSA/rest/CISEMessageService")
    @Key("counterpart.rest.url")
    String getCounterpartRestUrl();

    @DefaultValue("fr.sim-egn")
    @Key("simulator.message.dir")
    String getSimulatorMessageDir();

    @DefaultValue("fr.sim-egn")
    @Key("simulator.payload.dir")
    String getSimulatorPayloadDir();


    //** ==================  Signature configuration  ===================
    //signature.message.send = false
    @DefaultValue("false")
    @Key("signature.message.send")
    String isSignatureMessageSend();

    // signature.message.receive = false
    @DefaultValue("false")
    @Key("signature.message.receive")
    String isSignatureMessageReceive();

    // signature.message.receive = false
    @DefaultValue("false")
    @Key("signature.keyStoreFileName")
    String getKeyStoreName();

    // signature.message.receive = false
    @DefaultValue("false")
    @Key("signature.keyStorePassword")
    String getKeyStorePassword();


    // signature.message.receive = false
    @DefaultValue("false")
    @Key("signature.certificateKey")
    String getPrivateKeyAlias();


    // signature.message.receive = false
    @DefaultValue("false")
    @Key("signature.certificatePassword")
    String getPrivateKeyPassword();

    @DefaultValue("fr.sim-egn")
    @Key("simulator.id")
    String getSimulatorId();

    @DefaultValue("http://localhost:8080/sim-EGN/${simulator.mode}/CISEMessageService")
    @Key("webapp.ws.endpoint")
    String getSelfEndpoint();

    @Key("gateway-processor.submission.class")
    @DefaultValue("jrc.cise.gw.RabbitMQProcessor")
    String getSubmissionGatewayProcessor();

    static SimConfig createMyConfig(String myPath) {
        ConfigFactory.setProperty("conf.dir", myPath.toString());
        return ConfigFactory.create(SimConfig.class);
    }
}
