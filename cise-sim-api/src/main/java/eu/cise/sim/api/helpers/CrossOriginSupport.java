package eu.cise.sim.api.helpers;

import ch.qos.logback.classic.Logger;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Cross-origin resource sharing (CORS) is a mechanism that allows restricted
 * resources (involvedEvent.g. fonts, JavaScript, etc.) on a web page to be requested from
 * another domain outside the domain from which the resource originated.
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/Cross-origin_resource_sharing">
 * wikipedia CORS</a>
 * <p>
 * A CISE HTTP Server is likely to be called by HTML pages not directly served
 * by the current server and so it's necessary to specify an allow-origin all
 * to allow any domain to call the RESTful service
 */
public class CrossOriginSupport {

    private final Environment environment;

    public CrossOriginSupport(Environment environment) {
        this.environment = environment;
    }

    public void setup() {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        String name;
        Logger logger = (Logger) LoggerFactory.getLogger(CrossOriginSupport.class.getName());
        logger.debug("added the filter");
        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

    public static void setup(Environment env) {
        new CrossOriginSupport(env).setup();
    }

}
