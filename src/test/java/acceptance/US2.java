package acceptance;


import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.interfaces.Scraper;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


class US2 {

    ScraperDiscoverer scraperDiscoverer;

    @BeforeEach
    public void setUp() {
        scraperDiscoverer = new ScraperDiscoverer();
    }

    @Test
    void CA1() {
        String nonExistingPath = "non-existent-location/";

        assertThrows(FileNotFoundException.class, () -> scraperDiscoverer.discover(nonExistingPath));
    }
    @Test
    void CA2() {
        String invalidPath = "a,,rc.h.i./.vo.txt";
        assertThrows(FileNotFoundException.class, () -> scraperDiscoverer.discover(invalidPath));
    }
    @Test
    void CA3() {
        String emptyFolderPath = "src/test/resources/empty-folder";

        assertTrue(scraperDiscoverer.discover(emptyFolderPath).isEmpty());
    }
    @Test
    void CA4() {
        String notScraperPath = "src/test/resources/not-scraper";

        assertTrue(scraperDiscoverer.discover(notScraperPath).isEmpty());
    }
    @Test
    void CA5() {
        String simpleScraperPath = "src/test/resources/simple-scraper";

        Set<Scraper> scrapers = scraperDiscoverer.discover(simpleScraperPath);
        assertEquals(1, scrapers.size());
    }
    @Test
    void CA6() {
        String multipleScraperPath = "src/test/resources/multiple-scraper";

        Set<Scraper> scrapers = scraperDiscoverer.discover(multipleScraperPath);
        assertEquals(3, scrapers.size());
    }
}
