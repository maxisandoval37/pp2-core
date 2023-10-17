package acceptance;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.discovery.ScraperDiscoverer;
import entities.Scraper;
import entities.criteria.DiscoverCriteria;


class US2 {

    ScraperDiscoverer scraperDiscoverer;

    @BeforeEach
    public void setUp() {
        scraperDiscoverer = new ScraperDiscoverer();
    }

    @Test
    void CA1_DiscoveryOnNonExistentLocation_ShouldThrow_FileNotFoundException() {
        String nonExistingPath = "non-existent-location/";
        DiscoverCriteria testCriteria = getTestCriteria(nonExistingPath);

        assertThrows(FileNotFoundException.class, () -> scraperDiscoverer.discover(testCriteria));
    }

    @Test
    void CA2_DiscoveryOnInvalidPath_ShouldThrow_IllegalArgumentException() {
        String invalidPath = "archivo,txt";
        DiscoverCriteria testCriteria = getTestCriteria(invalidPath);

        assertThrows(IllegalArgumentException.class, () -> scraperDiscoverer.discover(testCriteria));
    }

    @Test
    void CA3_DiscoveryOnEmptyFolder_ShouldReturn_EmptySet() throws FileNotFoundException {
        String emptyFolderPath = "src/test/resources/empty-folder";
        DiscoverCriteria testCriteria = getTestCriteria(emptyFolderPath);

        Set<Scraper> result = scraperDiscoverer.discover(testCriteria);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA4_DiscoveryOnFolderWithNonScraperFile_ShouldReturn_EmptySet() throws FileNotFoundException {
        String notScraperPath = "src/test/resources/not-scraper";
        DiscoverCriteria testCriteria = getTestCriteria(notScraperPath);

        Set<Scraper> result = scraperDiscoverer.discover(testCriteria);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA5_DiscoveryOnFolderWithOneScraperFile_ShouldReturn_SetWithONEScraper() throws FileNotFoundException {
        String simpleScraperPath = "src/test/resources/simple-scraper";
        DiscoverCriteria testCriteria = getTestCriteria(simpleScraperPath);

        Set<Scraper> result = scraperDiscoverer.discover(testCriteria);

        assertEquals(1, result.size());
    }

    @Test
    void CA6_DiscoveryOnFolderWithTwoScraperFiles_ShouldReturn_SetWithTWOScrapers() throws FileNotFoundException {
        String multipleScraperPath = "src/test/resources/multiple-scraper";
        DiscoverCriteria testCriteria = getTestCriteria(multipleScraperPath);

        Set<Scraper> scrapers = scraperDiscoverer.discover(testCriteria);
        assertEquals(3, scrapers.size());
    }

    private DiscoverCriteria getTestCriteria(String nonExistingPath) {
        DiscoverCriteria criteria = new DiscoverCriteria();
        criteria.setPath(nonExistingPath);
        return criteria;
    }
}