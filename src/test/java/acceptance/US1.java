package acceptance;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import service.factory.ProductFactory;
import entities.Product;
import entities.Shop;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class US1 {

    private Shoppinator shoppinator;
    private Set<Shop> shops;
    private String[] productsToSearch;

    @BeforeEach
    public void setUp() throws FileNotFoundException {

        String path = "src/test/resources/multiple-scraper/";
        shoppinator = new Shoppinator(path);
        shops = shoppinator.getShops();
        productsToSearch = new String[]{"a", "b", "e"};
    }

    @Test
    void CA1_shouldListProductsOrderedByPrice() {
        List<Product> products = shoppinator.search(productsToSearch[0]);
        ProductFactory productFactory = new ProductFactory();
        String json = "[{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"price\":799.99,\"product_image_url\":\"https://example.com/\"},{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"price\":799.99,\"product_image_url\":\"https://example.com/\"}]";

        List<Product> productsMock = productFactory.create(json);

        Long[] prices = getProductPrices(products);

        assertEquals("a", productsToSearch[0]);
        assertTrue(!products.isEmpty());
        assertEquals(productsMock, products);
        assertTrue(prices[0] <= prices[1]);
    }

    @Test
    void CA2_shouldNotAddNewProductsToShops() {
        List<Product> products = shoppinator.search(productsToSearch[2]);

        Shop shopC = null;
        for (Shop shop : shops) {
            if (shop.getProducts().size() == 0) {
                shopC = shop;
            }
        }

        assertEquals("e", productsToSearch[2]);
        assertTrue(products.isEmpty());
        assertEquals(products.size(), shopC.getProducts().size());
    }

    @Test
    void CA3_shouldAddNewProductsOnInitialization() {
        List<Product> products = shoppinator.getProducts();

        ProductFactory productFactory = new ProductFactory();
        String json = "[{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"price\":799.99,\"product_image_url\":\"https://example.com/\"},{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"price\":799.99,\"product_image_url\":\"https://example.com/\"}"
            + ",{\"name\":\"b\",\"post_url\":\"https://example.com/\",\"price\":1299,\"product_image_url\":\"https://example.com/\"},{\"name\":\"b\",\"post_url\":\"https://example.com/\",\"price\":1299,\"product_image_url\":\"https://example.com/\"}]";

        List<Product> productsMock = productFactory.create(json);

        assertTrue(!products.isEmpty());
        assertEquals(productsMock, products);
    }

    private Long[] getProductPrices(List<Product> products) {
        Long[] prices = new Long[products.size()];

        for (int i = 0; i < products.size(); i++) {
            prices[i] = products.get(i).getProductPresentation().getPrice();
        }

        return prices;
    }
}