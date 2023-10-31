package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entities.ShopScraper;
import shoppinator.core.Shoppinator;
import entities.Scraper;
import entities.Shop;

class US5 {

    private Shoppinator shoppinator;
    private List<String> defaultShop, newShop, mutipleShops;

    @BeforeEach
    public void setUp() {
        shoppinator = new Shoppinator(shops);
        defaultShop = Arrays.asList("fravega");
        newShop = Arrays.asList("garbarino");
        mutipleShops = Arrays.asList("fravega", "garbarino");
    }

    @Test
    void CA1_shouldOnlyBeChoosedDefaultShopBecauseItWasTheSelectedOne() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator(shops);
        shoppinator.search("plugins/default", "webcam", "1000", "100000", "fravega");
        Set<Shop> shops = shoppinator.getShops();

        assertTrue(shops.size() == 1);
        assertEquals("fravega" , defaultShop.get(0));
        for (Shop aShop : shops) {
            assertEquals(defaultShop.get(0), getShopName((ShopScraper) aShop));
        }
    }

    @Test
    void CA2_shouldOnlyBeChoosedTheDefaultShopBecauseAnotherShopWasNotChoosed() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator(shops);
        shoppinator.search("plugins/default", "webcam", "1000", "100000", "fravega");
        Set<Shop> shops = shoppinator.getShops();

        assertTrue(shops.size() == 1);
        assertEquals("fravega" , defaultShop.get(0));
        for (Shop aShop : shops) {
            assertEquals(defaultShop.get(0), getShopName((ShopScraper) aShop));
        }
    }

    @Test
    void CA3_shouldBeChoosedAnotherShopDifferentThanTheDefaultOne() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator(shops);
        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino");
        Set<Shop> shops = shoppinator.getShops();

        assertTrue(shops.size() == 1);
        assertEquals("garbarino" , newShop.get(0));
        for (Shop aShop : shops) {
            assertEquals(newShop.get(0), getShopName((ShopScraper) aShop));
        }
    }

    @Test
    void CA4_shouldBeChoosedMoreThanOneShop() throws FileNotFoundException, URISyntaxException {
        shoppinator = new Shoppinator(shops);
        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino", "fravega");
        Set<Shop> shops = shoppinator.getShops();

        assertTrue(shops.size() == 2);
        for (Shop aShop : shops) {
            assertTrue(mutipleShops.contains(getShopName((ShopScraper) aShop)));
        }
    }

    private String getShopName(ShopScraper shop) throws URISyntaxException {
        Scraper scraper = shop.getScraper();
        URI uri = new URI(scraper.getUrl());
        String domain = uri.getHost();
        domain = domain.startsWith("www.") ? domain.substring(4) : domain;

        return domain.substring(0, domain.indexOf('.'));
    }
}