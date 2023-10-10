package acceptance;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.discovery.ScraperDiscoverer;
import shoppinator.core.interfaces.Scraper;


class US2 {

    ScraperDiscoverer scraperDiscoverer;

    @BeforeEach
    public void setUp() {
        scraperDiscoverer = new ScraperDiscoverer();
    }

    @Test
    void CA1_DiscoveryOnNonExistentLocation_ShouldThrow_FileNotFoundException() {
        String nonExistingPath = "non-existent-location/";

        assertThrows(FileNotFoundException.class, () -> scraperDiscoverer.discover(nonExistingPath));
    }

    @Test
    void CA2_DiscoveryOnInvalidPath_ShouldThrow_IllegalArgumentException() {
        String invalidPath = "a,,rc.h.i./.vo.txt";
        assertThrows(IllegalArgumentException.class, () -> scraperDiscoverer.discover(invalidPath));
    }

    @Test
    void CA3_DiscoveryOnEmptyFolder_ShouldReturn_EmptySet() throws FileNotFoundException {
        String emptyFolderPath = "src/test/resources/empty-folder";

        Set<Scraper> result = scraperDiscoverer.discover(emptyFolderPath);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA4_DiscoveryOnFolderWithNonScraperFile_ShouldReturn_EmptySet() throws FileNotFoundException {
        String notScraperPath = "src/test/resources/not-scraper";

        Set<Scraper> result = scraperDiscoverer.discover(notScraperPath);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA5_DiscoveryOnFolderWithOneScraperFile_ShouldReturn_SetWithONEScraper() throws FileNotFoundException {
        String simpleScraperPath = "src/test/resources/simple-scraper";

        Set<Scraper> result = scraperDiscoverer.discover(simpleScraperPath);

        assertEquals(1, result.size());
    }

    @Test
    void CA6_DiscoveryOnFolderWithTwoScraperFiles_ShouldReturn_SetWithTWOScrapers() throws FileNotFoundException {
        String multipleScraperPath = "src/test/resources/multiple-scraper";

        Set<Scraper> scrapers = scraperDiscoverer.discover(multipleScraperPath);
        assertEquals(3, scrapers.size());
    }
}
