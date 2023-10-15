package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.ShopScraper;
import shoppinator.core.Shoppinator;
import shoppinator.core.interfaces.Scraper;
import shoppinator.core.interfaces.Shop;

class US5 {

    private Shoppinator shoppinator;
    private List<String> defaultShop;
    private List<String> newShop;
    private List<String> mutipleShops;

    @BeforeEach
    public void setUp() {
        shoppinator = new Shoppinator();
        defaultShop = List.of("fravega");
        newShop = List.of("garbarino");
        mutipleShops = Arrays.asList("fravega", "garbarino");
    }

    @Test
    void CA1_defaultShopShouldBeChosenWhenItIsTheSelectedOne()
        throws FileNotFoundException, URISyntaxException {
        String[] testParams = {"plugins/default", "webcam", "1000", "100000", "fravega"};

        shoppinator.search(testParams);

        Set<Scraper> scrapers = getScrapersByShops(shoppinator.getShops());
        assertTrue(scrapers.size() == 1);
        assertEquals("fravega", defaultShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(defaultShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA2_defaultShopShouldBeChosenWhenNoOtherShopIsSelected()
        throws FileNotFoundException, URISyntaxException {
        String[] testParams = {"plugins/default", "webcam", "1000", "100000", "fravega"};

        shoppinator.search(testParams);

        Set<Scraper> scrapers = getScrapersByShops(shoppinator.getShops());
        assertTrue(scrapers.size() == 1);
        assertEquals("fravega", defaultShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(defaultShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA3_differentShopShouldBeChosenInsteadOfTheDefaultOne() throws FileNotFoundException, URISyntaxException {
        String[] testParams = {"plugins/availables", "webcam", "1000", "100000", "garbarino"};

        shoppinator.search(testParams);

        Set<Scraper> scrapers = getScrapersByShops(shoppinator.getShops());

        assertTrue(scrapers.size() == 1);
        assertEquals("garbarino", newShop.get(0));
        for (Scraper aScraper : scrapers) {
            assertEquals(newShop.get(0), getShopName(aScraper));
        }
    }

    @Test
    void CA4_multipleShopsShouldBeAbleToBeChosen() throws FileNotFoundException, URISyntaxException {
        String[] testParams = {"plugins/availables", "webcam", "1000", "100000", "garbarino", "fravega"};

        shoppinator.search(testParams);

        Set<Scraper> scrapers = getScrapersByShops(shoppinator.getShops());
        assertTrue(scrapers.size() == 2);
        for (Scraper aScraper : scrapers) {
            assertTrue(mutipleShops.contains(getShopName(aScraper)));
        }
    }

    private Set<Scraper> getScrapersByShops(Set<Shop> shops) {
        return shops.stream()
            .map(shop -> ((ShopScraper) shop).getScraper())
            .collect(Collectors.toSet());
    }

    private String getShopName(Scraper scraper) throws URISyntaxException {
        URI uri = new URI(scraper.getUrl());
        String domain = uri.getHost();
        domain = domain.startsWith("www.") ? domain.substring(4) : domain;

        return domain.substring(0, domain.indexOf('.'));
    }
}