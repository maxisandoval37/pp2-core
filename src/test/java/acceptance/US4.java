package acceptance;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.model.Product;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

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
        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino");
        List<Product> retrievedProducts = shoppinator.getProductList();
        for (Product product : retrievedProducts) {
            assertTrue(predicator.testPrice(product, x-> x <=  50L));
        }
    }

    @Test
    void CA2_filterProductsWithAPriceGreaterThanASpecifiedValue() throws FileNotFoundException {
        String longMaxValue = String.valueOf(Long.MAX_VALUE);
        shoppinator.search(path, aProduct, "50", longMaxValue, shopName);
        List<Product> retrievedProducts = shoppinator.getProductList();
        for (Product product : retrievedProducts) {
            assertTrue(predicator.testPrice(product, x-> x >=  50L));
        }
    }

    @Test
    void CA3_filterProductsInAPriceRange() throws FileNotFoundException {
        shoppinator.search(path, aProduct, "50", "100", shopName);
        List<Product> retrievedProducts = shoppinator.getProductList();
        for (Product product : retrievedProducts) {
            assertTrue(
                    predicator.testPrice(product, x-> x >= 50L &&
                            x <= 100L
                    )
            );
        }
    }



    public static class Predicator {

        public Predicator() {}
        public boolean testPrice(Product product, Predicate<Long> predicate) {

            return predicate.test(product.getProductPresentation().getPrice());
        }
    }

}