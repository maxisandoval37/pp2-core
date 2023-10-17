package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import entities.Product;

class US4 {

    private String aProduct;
    private Shoppinator shoppinator;
    private Predicator predicator;
    private String path;
    private String shopName;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        aProduct = "Notebook";
        path = "plugins/availables";
        shopName = "garbarino";
        shoppinator = new Shoppinator();
        shoppinator.init("plugins/default/");
        predicator = new Predicator();
    }

    @Test
    void CA1_filterProductsWithAPriceLessThanOrEqualToASpecifiedValue() throws FileNotFoundException {
        String[] testParams = {path, "webcam", "0", "50", shopName};

        shoppinator.search(testParams);
        List<Product> retrievedProducts = shoppinator.getProductList();

        for (Product product : retrievedProducts) {
            assertTrue(predicator.testPrice(product, x -> x <= 50L));
        }
    }

    @Test
    void CA2_filterProductsWithAPriceGreaterThanASpecifiedValue() throws FileNotFoundException {
        String longMaxValue = String.valueOf(Long.MAX_VALUE);
        String[] testParams = {path, aProduct, "50", longMaxValue, shopName};

        shoppinator.search(testParams);
        List<Product> retrievedProducts = shoppinator.getProductList();

        for (Product product : retrievedProducts) {
            assertTrue(predicator.testPrice(product, x -> x >= 50L));
        }
    }

    @Test
    void CA3_filterProductsInAPriceRange() throws FileNotFoundException {
        String[] testParams = {path, aProduct, "50", "100", shopName};

        shoppinator.search(testParams);
        List<Product> retrievedProducts = shoppinator.getProductList();

        for (Product product : retrievedProducts) {
            assertTrue(predicator.testPrice(product, x -> x >= 50L && x <= 100L));
        }
    }

    private static class Predicator {

        public Predicator() {
        }

        public boolean testPrice(Product product, Predicate<Long> predicate) {

            return predicate.test(product.getProductPresentation().getPrice());
        }
    }

}
