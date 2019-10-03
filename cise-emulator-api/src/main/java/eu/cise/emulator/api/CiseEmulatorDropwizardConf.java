package eu.cise.emulator.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import eu.cise.emulator.EmuConfig;
import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.io.MessageStorage;
import eu.cise.emulator.templates.TemplateLoader;
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

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();
    private MessageProcessor messageProcessor;
    private MessageStorage messageStorage;
    private TemplateLoader templateLoader;
    private EmuConfig emuConfig;

    public MessageStorage getMessageStorage() {
        return messageStorage;
    }

    public void setMessageStorage(MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
    }

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

    public EmuConfig getEmuConfig() {
        return this.emuConfig;
    }

    public void setEmuConfig(EmuConfig emuConfig) {
        this.emuConfig = emuConfig;
    }

    public TemplateLoader getTemplateLoader() {
        return templateLoader;
    }

    public void setTemplateLoader(TemplateLoader templateLoader) {
        this.templateLoader = templateLoader;
    }
}
