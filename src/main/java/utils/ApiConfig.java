package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConfig {
    private final Properties properties = new Properties();

    public ApiConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                LoggerUtil.error("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            LoggerUtil.error(ex.getMessage(), ex);
        }
    }

    public String getEnvironment() {
        return properties.getProperty("environment");
    }

    public String getAuthorization() {
        return properties.getProperty("Authorization");
    }
}
