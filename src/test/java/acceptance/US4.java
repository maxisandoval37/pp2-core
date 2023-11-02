package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.getTestSearchParams;

import entities.Result;
import java.io.FileNotFoundException;
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
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory("a");
        shoppinator = shoppinatorFactory.create("src/test/resources/multiple-shops/");
    }

    @Test
    void CA1_filterProductsWithAPriceLessThanASpecifiedValue() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams("webcam", 0L, 100L));

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice() <= 100L));
    }

    @Test
    void CA2_filterProductsWithAPriceGreaterThanASpecifiedValue() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams("Notebook", defaultMinPrice, 50L));

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice() >= 50L));
    }

    @Test
    void CA3_filterProductsWithinAPriceRange() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams("Notebook", 50L, 100L));

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getPrice() >= 50L && x.getPrice() <= 100L));
    }

    @Test
    void CA3_filterProductsWithinDefaultPriceRange() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams("Notebook"));

        assertTrue(retrievedProducts.stream()
            .allMatch(x -> x.getPrice() >= defaultMinPrice && x.getPrice() <= defaultMaxPrice));
    }

}
