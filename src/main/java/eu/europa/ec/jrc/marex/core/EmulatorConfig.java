package eu.europa.ec.jrc.marex.core;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

    @LoadPolicy(LoadType.FIRST)
    @Sources({"file://${emuapp.file}/emuapp.properties","classpath:emuapp.properties"})
    public interface EmulatorConfig extends Config {

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

}
