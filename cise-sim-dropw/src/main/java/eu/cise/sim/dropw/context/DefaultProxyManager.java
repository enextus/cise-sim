package eu.cise.sim.dropw.context;

import eu.cise.sim.config.ProxyManager;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class DefaultProxyManager implements ProxyManager {

    private final String host;
    private final String port;

    public static void checkConfiguration(String host, String port) {

        if (!StringUtils.isEmpty(host) || !StringUtils.isEmpty(port)) {
            Pattern p = Pattern.compile("^"
                    + "(([0-9]{1,3}\\.){3})[0-9]{1,3}" // Ip
                    + ":"
                    + "[0-9]{1,5}$"); // Port

            if (!p.matcher(host + ":" + port).matches()) {
                throw new ConfigurationException("PROXY: wrong couple host and port configuration host[" + host + "] port[" + port + "]");
            }
        }
    }

    public DefaultProxyManager(String host, String port) {

        checkConfiguration(host, port);

        this.host = host;
        this.port = port;
    }

    @Override
    public String activate() {

        String result = "PROXY: no proxy configured";
        if (!StringUtils.isEmpty(host)) {

            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);
            result = "PROXY: activated on host[" + host + "] port[" + port + "]";
        }
        return result;
    }
}
