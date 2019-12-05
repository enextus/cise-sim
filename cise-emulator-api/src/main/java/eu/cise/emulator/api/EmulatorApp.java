package eu.cise.emulator.api;

import com.codahale.metrics.health.HealthCheck;
import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.emulator.AppContext;
import eu.cise.emulator.DefaultAppContext;
import eu.cise.emulator.api.helpers.CrossOriginSupport;
import eu.cise.emulator.api.resources.MessageResource;
import eu.cise.emulator.api.resources.TemplateResource;
import eu.cise.emulator.api.resources.UIServiceResource;
import eu.cise.emulator.api.resources.UiMessageResource;
import eu.cise.emulator.api.soap.CISEMessageServiceSoapImplDefault;
import eu.cise.emulator.io.MessageStorage;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;

public class EmulatorApp extends Application<EmulatorConf> {

    // JAX-WS Bundle
    private final JAXWSBundle<Object> jaxwsBundle = new JAXWSBundle<>("/api/soap");

    public static void main(final String[] args) {
        try {
            new EmulatorApp().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "CISE Sim";
    }

    @Override
    public void initialize(final Bootstrap<EmulatorConf> bootstrap) {
        bootstrap.addBundle(jaxwsBundle);
        bootstrap.addBundle(
            new ConfiguredAssetsBundle(
                "/assets/",
                "/",
                "index.html")); // imply redirect from root ?
    }

    @Override
    public void run(final EmulatorConf conf, final Environment environment) {
        CrossOriginSupport.setup(environment);

        environment.jersey().setUrlPattern("/api");

        AppContext appCtx = new DefaultAppContext();
        XmlMapper xmlMapper = appCtx.getXmlMapper();
        MessageStorage messageStorage = appCtx.makeMessageStorage();
        MessageAPI messageAPI = new DefaultMessageAPI(
            appCtx.makeMessageProcessor(),
            messageStorage,
            appCtx.makeTemplateLoader(),
            xmlMapper,
            appCtx.getPrettyNotValidatingXmlMapper());

        environment.healthChecks().register("noop", new HealthCheck() {
            @Override
            protected Result check() {
                return Result.healthy();
            }
        });
        environment.jersey().register(new UiMessageResource(messageAPI));
        environment.jersey().register(new UIServiceResource(appCtx.makeEmuConfig()));
        environment.jersey().register(new MessageResource(messageAPI, messageStorage));
        environment.jersey().register(
            new TemplateResource(messageAPI,
                new TemplateAPI(
                    appCtx.makeMessageProcessor(),
                    appCtx.makeTemplateLoader(),
                    xmlMapper, appCtx.getPrettyNotValidatingXmlMapper())));

        CISEMessageServiceSoapImpl ciseMessageServiceSoap = new CISEMessageServiceSoapImplDefault(
            messageAPI, appCtx.getPrettyNotValidatingXmlMapper());

        // WSDL first service using server side JAX-WS handler and CXF logging interceptors
        jaxwsBundle.publishEndpoint(new EndpointBuilder("messages", ciseMessageServiceSoap));

        environment.lifecycle().addLifeCycleListener(new Listener() {
            @Override
            public void lifeCycleStarting(LifeCycle lifeCycle) {

            }

            @Override
            public void lifeCycleStarted(LifeCycle lifeCycle) {
                System.out.println("== API Server started ===========================");
            }

            @Override
            public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {

            }

            @Override
            public void lifeCycleStopping(LifeCycle lifeCycle) {

            }

            @Override
            public void lifeCycleStopped(LifeCycle lifeCycle) {

            }
        });

    }

}