package eu.cise.emulator.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This class creates and destroys the WireMock server programmatically. It also
 * programmatically retrieves unmatched requests from the WireMock server and
 * logs these as errors.
 * An alternative is to use the WireMock server as a JUnit @Rule, please refer to {@link WireMockJUnit4WithRuleTests} for an example.
 */
public class WireMockJUnit4Tests extends AbstractTestBase {
    /* Constant(s): */
    private static final Logger LOGGER = LoggerFactory.getLogger(WireMockJUnit4Tests.class);
    protected static final String TESTFILES_BASE ="tmp/wiremocktest/";
    public static final String EXCHANGE_RATE = "4.123";

    /* Instance variable(s): */
    protected WireMockServer mWireMockServer;

    /**
     * Performs preparations before each test.
     */
    @Before
    public void setup() {
        initializeRestAssuredHttp();

        /*
         * Create the WireMock server to be used by a test.
         * This also ensures that the records of received requests kept by the WireMock server and expected scenarios etc are cleared prior to each test.
         * An alternative is to create the WireMock server once before all the tests in a test-class and call {@code resetAll} before each test.
         */
        mWireMockServer = new WireMockServer(HTTP_ENDPOINT_PORT);
        mWireMockServer.start();
    }

    /**
     * Performs cleanup after each test.
     */
    @After
    public void tearDown() {
        /* Stop the WireMock server. */
        mWireMockServer.stop();

        /*
         * Find all requests that were expected by the WireMock server but that were
         * not matched by any request actually made to the server.
         * Logs any such requests as errors.
         */
        final List<LoggedRequest> theUnmatchedRequests = mWireMockServer.findAllUnmatchedRequests();
        if (!theUnmatchedRequests.isEmpty()) {
            LOGGER.error("Unmatched requests: {}", theUnmatchedRequests);
        }
    }
}