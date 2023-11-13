package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US4 {

    long defaultMinPrice = 0L;
    long defaultMaxPrice = Long.MAX_VALUE;

    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/US4/");
    }

    @Test
    void CA1_filterProductsWithAPriceLessThanASpecifiedValue() {
        List<Article> retrievedProducts = shoppinator.search("webcam -0 +100");

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice().compareTo(new BigDecimal(100)) <= 0));
    }

    @Test
    void CA2_filterProductsWithAPriceGreaterThanASpecifiedValue() {
        List<Article> retrievedProducts = shoppinator.search("Notebook +50");

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice().compareTo(new BigDecimal(50)) >= 0));
    }

    @Test
    void CA3_filterProductsWithinAPriceRange() {
        List<Article> retrievedProducts = shoppinator.search("Notebook -50 +100");

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice().compareTo(new BigDecimal(50)) >= 0
            && x.getPrice().compareTo(new BigDecimal(100)) <= 0));
    }

    @Test
    void CA3_filterProductsWithinDefaultPriceRange() {
        List<Article> retrievedProducts = shoppinator.search("Notebook");

        assertTrue(retrievedProducts.stream()
            .allMatch(x -> x.getPrice().compareTo(new BigDecimal(defaultMinPrice)) >= 0
                && x.getPrice().compareTo(new BigDecimal(defaultMaxPrice)) <= 0));
    }

}
