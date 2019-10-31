package eu.cise.emulator.api;

import com.codahale.metrics.health.HealthCheck;
import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.emulator.AppContext;
import eu.cise.emulator.DefaultAppContext;
import eu.cise.emulator.api.helpers.CrossOriginSupport;
import eu.cise.emulator.api.resources.*;
import eu.cise.emulator.io.MessageStorage;
import eu.eucise.xml.XmlMapper;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

import javax.xml.ws.Endpoint;

public class EmulatorApp extends Application<EmulatorConf> {

    // JAX-WS Bundle
    private static final JAXWSBundle<Object> JAXWS_BUNDLE = new JAXWSBundle<>("/api/soap");

    public static void main(final String[] args) throws Exception {
        System.out.println("\n==============================================");
        new EmulatorApp().run(args);
    }

    @Override
    public void initialize(final Bootstrap<EmulatorConf> bootstrap) {
        bootstrap.addBundle(JAXWS_BUNDLE);
        bootstrap.addBundle(
                new ConfiguredAssetsBundle("/assets/", "/",
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
        environment.jersey().register(new UiServiceResource(appCtx.makeEmuConfig()));
        environment.jersey().register(new MessageResource(messageAPI, messageStorage));
        environment.jersey().register(
                new TemplateResource(messageAPI,
                        new TemplateAPI(
                                appCtx.makeMessageProcessor(),
                                appCtx.makeTemplateLoader(),
                                xmlMapper, appCtx.getPrettyNotValidatingXmlMapper())));

        //SOAP rel
        CISEMessageServiceSoapImpl ciseMessageServiceSoap = new CISEMessageServiceSoapImplDefault(messageAPI,xmlMapper);
        //if (configuration.getTransportMode().toUpperCase().contains("SOAP")  ) { // WSDL first service using server side JAX-WS handler and CXF logging interceptors
        Endpoint e = JAXWS_BUNDLE.publishEndpoint(
                new EndpointBuilder("messages", ciseMessageServiceSoap));
        //}
        environment.lifecycle().addServerLifecycleListener(server -> {
            System.out.println("==============================================\n");
        });

    }
}



