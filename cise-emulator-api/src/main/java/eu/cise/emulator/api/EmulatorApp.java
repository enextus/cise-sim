package eu.cise.emulator.api;

import eu.cise.emulator.AppContext;
import eu.cise.emulator.DefaultAppContext;
import eu.cise.emulator.api.helpers.CrossOriginSupport;
import eu.cise.emulator.api.resources.AssetRedirectionResource;
import eu.cise.emulator.api.resources.MessageResource;
import eu.cise.emulator.api.resources.TemplateResource;
import eu.cise.emulator.api.resources.WebAPIMessageResource;
import eu.cise.emulator.io.MessageStorage;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class EmulatorApp extends Application<EmulatorConf> {

    public static void main(final String[] args) throws Exception {
        new EmulatorApp().run(args);
    }

    @Override
    public void initialize(final Bootstrap<EmulatorConf> bootstrap) {
        bootstrap.addBundle(
                new ConfiguredAssetsBundle("/assets/", "/base/",
                "index.html")); // imply redirect from root ?
    }

    @Override
    public void run(final EmulatorConf conf, final Environment environment) {
        CrossOriginSupport.setup(environment);

        environment.jersey().setUrlPattern("/*");

        AppContext appCtx = new DefaultAppContext();
        XmlMapper xmlMapper = appCtx.getXmlMapper();
        MessageStorage messageStorage = appCtx.makeMessageStorage();

        MessageAPI messageAPI = new DefaultMessageAPI(
                appCtx.makeMessageProcessor(),
                messageStorage,
                appCtx.makeTemplateLoader(),
                xmlMapper);

        environment.jersey().register(new WebAPIMessageResource(messageAPI));
        environment.jersey().register(new MessageResource(messageAPI, messageStorage));

        environment.jersey().register(
                new TemplateResource(messageAPI,
                        new TemplateAPI(
                                appCtx.makeMessageProcessor(),
                                appCtx.makeTemplateLoader(),
                                xmlMapper)));

        environment.jersey().register(new AssetRedirectionResource());
    }
}



