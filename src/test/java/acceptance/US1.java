package acceptance;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.factory.ProductFactory;
import shoppinator.core.model.Product;
import shoppinator.core.interfaces.Shop;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class US1 {

    private Shoppinator shoppinator;
    private Set<Shop> shops;

    @BeforeEach
    public void setUp() {

        String path = "src/test/resources/scrapers/";
        shoppinator = new Shoppinator(path);
        shops = shoppinator.getShops();
    }

    @Test
    void testComparePrices() {
        List<Product> products = shoppinator.search("a");
        ProductFactory productFactory = new ProductFactory();
        String json = "[{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}},{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}}]";

        List<Product> productsMock = productFactory.create(json);

        assertFalse(products.isEmpty());
        assertEquals(productsMock, products);
        assertTrue(
            products.get(0).getProductPresentation().getPrice() <= products.get(1).getProductPresentation().getPrice());
    }


    @Test
    void testEmptySearchCA2() {
        List<Product> products = shoppinator.search("");

        Shop shopC = null;
        for (Shop shop : shops) {
            if (shop.getProducts().size() == 0) {
                shopC = shop;
            }
        }

        assertTrue(products.isEmpty());
        assertEquals(products.size(), shopC.getProducts().size());
    }

    @Test
    void testProductNotFoundCA3() {
        List<Product> products = shoppinator.search("e");

        Shop shopC = null;
        for (Shop shop : shops) {
            if (shop.getProducts().size() == 0) {
                shopC = shop;
            }
        }

        assertTrue(products.isEmpty());
        assertEquals(products.size(), shopC.getProducts().size());
    }

    @Test
    void testCoreInitializationCA4() {
        List<Product> products = shoppinator.search("featured");

        assertTrue(!products.isEmpty());
    }
}