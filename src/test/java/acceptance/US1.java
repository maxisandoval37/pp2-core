package acceptance;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import service.factory.ProductFactory;
import entities.Product;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.getTestParams;

class US1 {

    private Shoppinator shoppinator;
    private List<Product> products;
    private String[] productsToSearch;

    String path = "src/test/resources/scrapers/";

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        shoppinator = new Shoppinator();
        shoppinator.init(path);
        products = shoppinator.getProductList();
        productsToSearch = new String[]{"a", "b", "e"};
    }

    @Test
    void CA1_shouldListProductsOrderedByPrice() throws FileNotFoundException {
        String[] testParams = getTestParams(path, productsToSearch[0]);
        List<Product> products = shoppinator.search(testParams);
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
    void CA2_shouldNotAddNewProductsToShops() throws FileNotFoundException {
        String[] testParams = getTestParams(path, productsToSearch[2]);
        List<Product> retrievedProducts = shoppinator.search(testParams);

        assertEquals("e", productsToSearch[2]);
        assertTrue(retrievedProducts.isEmpty());
        assertEquals(shoppinator.getProductList().size(), retrievedProducts.size());
    }

    @Test
    void CA3_shouldAddNewProductsOnInitialization() {
        List<Product> products = shoppinator.getProductList();

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