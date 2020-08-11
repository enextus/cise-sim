package eu.cise.sim.dropw.context;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionApp {

    public String getVersion()  {

        String result;
        try {

        InputStream inputStream;
            Properties prop = new Properties();
            String propFileName = "version.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {

                prop.load(inputStream);
                result = prop.getProperty("app.version");
                if (StringUtils.isEmpty(result)) {
                    throw new ConfigurationException("Simulator version not configured");
                }
            } else {
                throw new ConfigurationException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            throw new ConfigurationException("property file with problems : " + e.getMessage());
        }

        return result;
    }
}
