package eu.cise.sim.dropw;

import com.codahale.metrics.health.HealthCheck;
import com.roskart.dropwizard.jaxws.EndpointBuilder;
import com.roskart.dropwizard.jaxws.JAXWSBundle;
import eu.cise.accesspoint.service.v1.CISEMessageServiceSoapImpl;
import eu.cise.sim.api.MessageAPI;
import eu.cise.sim.api.TemplateAPI;
import eu.cise.sim.api.history.ThreadMessageService;
import eu.cise.sim.config.ProxyManager;
import eu.cise.sim.dropw.context.AppContext;
import eu.cise.sim.dropw.context.ConfigurationException;
import eu.cise.sim.dropw.context.DefaultAppContext;
import eu.cise.sim.dropw.context.SimConf;
import eu.cise.sim.dropw.helpers.CrossOriginSupport;
import eu.cise.sim.dropw.restresources.*;
import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimApp extends Application<SimConf> {

    private final Logger logger = LoggerFactory.getLogger(SimApp.class);

    // JAX-WS Bundle
    private final JAXWSBundle<Object> jaxwsBundle = new JAXWSBundle<>("/api/soap");

    @Override
    public String getName() {
        return "CISE Sim";
    }

    @Override
    public void initialize(final Bootstrap<SimConf> bootstrap) {
        bootstrap.addBundle(jaxwsBundle);
        bootstrap.addBundle(
            new ConfiguredAssetsBundle(
                "/assets/",
                "/",
                "index.html")); // imply redirect from root ?
    }

    @Override
    public void run(final SimConf conf, final Environment environment) {
        CrossOriginSupport.setup(environment);

        environment.jersey().setUrlPattern("/api");

        AppContext appCtx;
        try {
            appCtx = new DefaultAppContext();
        } catch (ConfigurationException cex) {
            logger.error("Configuration error : " + cex.getMessage());
            throw cex;
        }

        // Proxy management
        ProxyManager proxyManager = appCtx.makeProxyManager();
        String logProxyActivation = proxyManager.activate();
        logger.info(logProxyActivation);

        ThreadMessageService threadMessageService =  appCtx.getThreadMessageService();
        MessageAPI  messageAPI = appCtx.getMessageAPI(threadMessageService);
        TemplateAPI templateAPI = appCtx.getTemplateAPI();

        environment.healthChecks().register("noop", new HealthCheck() {
            @Override
            protected Result check() {
                return Result.healthy();
            }
        });

        environment.jersey().register(new UiMessageResource(messageAPI));
        environment.jersey().register(new UIServiceResource(appCtx.makeEmuConfig()));
        environment.jersey().register(new MessageResource(messageAPI));
        environment.jersey().register(new TemplateResource(messageAPI, templateAPI));
        environment.jersey().register(new ThreadMessageResource(threadMessageService));
        environment.jersey().register(new JsonProcessingExceptionMapper(true));

        CISEMessageServiceSoapImpl ciseMessageServiceSoap = appCtx.getServiceSoap(messageAPI);

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

    public static void main(final String[] args) {

        SimApp simApp = new SimApp();
        try {
            simApp.run(args);
        } catch (ConfigurationException ce) {
            simApp.logger.error("Configuration error : {}", ce.getMessage());
        } catch (Exception e) {
            simApp.logger.error("FATAL ERROR ", e);
        }
    }
}