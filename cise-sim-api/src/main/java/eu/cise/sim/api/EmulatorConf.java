package eu.cise.sim.api;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EmulatorConf extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();


    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }

}
