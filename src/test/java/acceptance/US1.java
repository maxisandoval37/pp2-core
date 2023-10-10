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
    public void setUp() throws FileNotFoundException {

        String path = "src/test/resources/scrapers/";
        shoppinator = new Shoppinator(path);
        shops = shoppinator.getShops();
    }

    @Test
    void CA1_shouldListProductsOrderedByPrice() {
        List<Product> products = shoppinator.search("a");
        ProductFactory productFactory = new ProductFactory();
        String json = "[{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}},{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}}]";

        List<Product> productsMock = productFactory.create(json);

        float[] prices = getProductPrices(products);

        assertFalse(products.isEmpty());
        assertEquals(productsMock, products);
        assertTrue(prices[0] <= prices[1]);
    }

    @Test
    void CA2_shouldNotAddNewProductsToShops() {
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
    void CA3_shouldAddNewProductsOnInitialization() {
        List<Product> products = shoppinator.search("featured");

        assertTrue(!products.isEmpty());
    }

    private float[] getProductPrices(List<Product> products) {
        float[] prices = new float[products.size()];

        for (int i = 0; i < products.size(); i++) {
            prices[i] = products.get(i).getProductPresentation().getPrice();
        }

        return prices;
    }
}