package eu.cise.emulator.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.MessageProcessor;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * CiseEmulatorDropwizardConf
 * general purpose class
 * extending Configuration (dropwizard)
 */

//TODO:create a subclass for WebApi correlated modality

public class CiseEmulatorDropwizardConf extends Configuration implements AssetsBundleConfiguration {


    MessageProcessor messageProcessor;

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();

    public AssetsConfiguration getAssets() {
        return assets;
    }


    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }


    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }


}
