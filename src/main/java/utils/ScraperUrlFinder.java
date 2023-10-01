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
public class ScraperUrlFinder {

    public static String findUrl(String scraperName) {
        String prefix = scraperName.replace("Scraper", "").toLowerCase();
        return findUrlFromScraperName(prefix);
    }

    private static String findUrlFromScraperName(String prefix) {
        Properties prop = new Properties();
        Path path = Paths.get("src/main/resources/conf/config.properties"); // specify the correct path to your file

        try (InputStream is = Files.newInputStream(path)) {
            prop.load(is);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        for (String key : prop.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                return prop.getProperty(key);
            }
        }

        return null;
    }
}
