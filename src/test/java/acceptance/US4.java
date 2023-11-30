package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US4 {

    private List<Article> expected;
    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/US4/");
    }

    @Test
    void CA1_filterProductsWithAPriceLessThanASpecifiedValue() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "F",
            new BigDecimal(50)));
        expected.add(new Article("a", "G",
            new BigDecimal(100)));

        assertTrue(expected.equals(shoppinator.search("a +100")));
    }

    @Test
    void CA2_filterProductsWithAPriceGreaterThanASpecifiedValue() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "F",
            new BigDecimal(50)));
        expected.add(new Article("a", "G",
            new BigDecimal(100)));
        expected.add(new Article("a", "H",
            new BigDecimal(150)));

        assertTrue(expected.equals(shoppinator.search("a -50")));
    }

    @Test
    void CA3_filterProductsWithinAPriceRange() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "F",
            new BigDecimal(50)));
        expected.add(new Article("a", "G",
            new BigDecimal(100)));

        assertTrue(expected.equals(shoppinator.search("a -50 +100")));
    }

    @Test
    void CA4_filterProductsWithinDefaultPriceRange() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "F",
            new BigDecimal(50)));
        expected.add(new Article("a", "G",
            new BigDecimal(100)));
        expected.add(new Article("a", "H",
            new BigDecimal(150)));

        assertTrue(expected.equals(shoppinator.search("a")));
    }

    @Test
    void CA5_filterProductsWithinInvalidFilters() {
        expected = new ArrayList<>();

        assertTrue(expected.equals(shoppinator.search("a $500 &100")));
    }

}
