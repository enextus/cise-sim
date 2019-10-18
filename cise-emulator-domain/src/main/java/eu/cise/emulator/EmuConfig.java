package eu.cise.emulator;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Preprocessor;

/**
 * This file is containing the emulator application configuration
 */
@Config.PreprocessorClasses({EmuConfig.TrimAndInsureBoolean.class})
@Sources({"file:${conf.dir}emulator.properties",
        "classpath:emulator.properties"})
public interface EmuConfig extends Config {

    @Key("service.participantid")
    String participantId();

    @Key("destination.endpoint-url")
    String endpointUrl();

    @Key("signature.keyStoreFileName")
    String keyStoreFileName();

    @Key("signature.keyStorePassword")
    String keyStorePassword();

    @Key("signature.privateKeyAlias")
    String privateKeyAlias();

    @Key("signature.privateKeyPassword")
    String privateKeyPassword();

    @Key("template.messages.directory")
    String messageTemplateDir();

    @Key("validation.rule.date")
    @DefaultValue("false")
    boolean isDateValidationEnabled();

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
