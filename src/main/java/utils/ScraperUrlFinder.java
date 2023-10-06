package utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ScraperUrlFinder {

    public static String findUrl(String scraperName) {
        String prefix = scraperName.replace("Scraper", "").toLowerCase();
        return PropertiesHelper.getValueOfPropertyThatStartsWith(prefix);
    }

}
