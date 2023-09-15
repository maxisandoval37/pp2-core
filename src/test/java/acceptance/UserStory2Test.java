package acceptance;


import org.junit.jupiter.api.Test;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.interfaces.Scraper;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserStory2Test {



    @Test
    public void nonExistentLocationCA1() {

        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        //Assertions.assertThrows(FileNotFoundException.class, () -> { scraperDiscoverer.discover("nonExistentLocation"); });
    }
    @Test
    public void invalidLocationCA2() {

        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        //Assertions.assertThrows(IllegalArgumentException.class, () -> { scraperDiscoverer.discover("src/test/resources/"); });

    }
    @Test
    public void emptyFolderCA3() {
        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        assertTrue(scraperDiscoverer.discover("src/test/resources/carpetaVacia").isEmpty());

    }
    @Test
    public void itsNotScraperCA4() {

        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        assertTrue(scraperDiscoverer.discover("src/test/resources/noEsScraper").isEmpty());

    }
    @Test
    public void simpleScraperCA5() {

        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        Set<Scraper> scrapers = scraperDiscoverer.discover("src/test/resources/scraperSimple");
        assertEquals(1, scrapers.size());

    }
    @Test
    public void scrapersMultiplesCA6() {

        ScraperDiscoverer scraperDiscoverer = new ScraperDiscoverer();
        Set<Scraper> scrapers = scraperDiscoverer.discover("src/test/resources/scrapersMultiples");
        assertEquals(3, scrapers.size());

    }
}
