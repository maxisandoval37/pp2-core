package utils;

import java.io.IOException;
import java.io.InputStream;
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
        try(InputStream is = ScraperUrlFinder.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(is);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        for (String key : prop.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                return prop.getProperty(key);
            }
        }

        return null;
    }
}
