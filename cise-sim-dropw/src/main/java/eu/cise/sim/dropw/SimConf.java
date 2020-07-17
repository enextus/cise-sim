package eu.cise.sim.dropw;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * CiseSimDropwizardConf
 * general purpose class
 * extending Configuration (dropwizard)
 */
public class SimConf extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();


    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }

}
