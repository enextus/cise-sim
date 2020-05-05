package eu.cise.sim.config;

import eu.cise.dispatcher.DispatcherType;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Preprocessor;

/**
 * This file is containing the sim application configuration
 */
@Config.PreprocessorClasses({SimConfig.TrimAndInsureBoolean.class})
@Sources({"file:${conf.dir}sim.properties",
        "classpath:sim.properties",
        "file:./sim.properties"})
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

    @Key("signature.keystore.filename")
    String keyStoreFileName();

    @Key("signature.keystore.password")
    String keyStorePassword();

    @Key("signature.privatekey.alias")
    String privateKeyAlias();

    @Key("signature.privatekey.password")
    String privateKeyPassword();

    @Key("app.version")
    String appVersion();

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
