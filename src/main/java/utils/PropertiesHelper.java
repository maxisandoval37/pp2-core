package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PropertiesHelper {

    public static String getValue(String propertyName) {
        Properties prop = loadProperties();
        return getProperty(prop, propertyName);
    }

    public static String getValueOfPropertyThatStartsWith(String prefix) {
        Properties prop = loadProperties();
        for (String key : prop.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                return prop.getProperty(key);
            }
        }
        return null;
    }

    private static Properties loadProperties() {
        Properties prop = new Properties();
        Path path = Paths.get("src/main/resources/conf/config.properties");

        try (InputStream is = Files.newInputStream(path)) {
            prop.load(is);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return prop;
    }

    private static String getProperty(Properties prop, String propertyName) {
        for (String key : prop.stringPropertyNames()) {
            if (key.equals(propertyName)) {
                return prop.getProperty(key);
            }
        }
        return null;
    }
}
