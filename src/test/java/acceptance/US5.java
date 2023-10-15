package acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.model.Product;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class US5 {

    private Shoppinator shoppinator;
    private List<String> defaultShop, newShop, mutipleShops;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        shoppinator = new Shoppinator();
        defaultShop = Arrays.asList(new String[]{"fravega"});
        newShop = Arrays.asList(new String[]{"garbarino"});
        mutipleShops = Arrays.asList(new String[]{"fravega", "garbarino"});
    }

    @Test
    void CA1_shouldOnlyBeChoosedDefaultShopBecauseItWasTheSelectedOne() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator();
        shoppinator.search("plugins/default", "webcam", "1000", "100000", "fravega");
        Set<Scraper> scrapers = shoppinator.getScrapers();

        assertTrue(scrapers.size() == 1);
        assertEquals("fravega" , defaultShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(defaultShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA2_shouldOnlyBeChoosedTheDefaultShopBecauseAnotherShopWasNotChoosed() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator();
        shoppinator.search("plugins/default", "webcam", "1000", "100000", "fravega");
        Set<Scraper> scrapers = shoppinator.getScrapers();

        assertTrue(scrapers.size() == 1);
        assertEquals("fravega" , defaultShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(defaultShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA3_shouldBeChoosedAnotherShopDifferentThanTheDefaultOne() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator();
        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino");
        Set<Scraper> scrapers = shoppinator.getScrapers();

        assertTrue(scrapers.size() == 1);
        assertEquals("garbarino" , newShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(newShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA4_shouldBeChoosedMoreThanOneShop() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator();
        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino", "fravega");
        Set<Scraper> scrapers = shoppinator.getScrapers();

        assertTrue(scrapers.size() == 2);
        for (Scraper aScraper : scrapers) {
            assertTrue(mutipleShops.contains(getShopName(aScraper)));
        }
    }

    private String getShopName(Scraper aScraper) throws URISyntaxException {
        URI uri = new URI(aScraper.getUrl());
        String domain = uri.getHost();
        domain = domain.startsWith("www.") ? domain.substring(4) : domain;

        return domain.substring(0, domain.indexOf('.'));
    }
}