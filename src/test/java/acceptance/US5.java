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

class US5 {

    private List<Article> expected;
    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/US5/");
    }

    @Test
    void CA1_oneShopIsSelectedShouldSearchInOneShop() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "F", new BigDecimal(100)));

        shoppinator.setShops("F");
        assertTrue(expected.equals(shoppinator.search("a")));
    }

    @Test
    void CA2_noShopsAreSelectedShouldSearchInAllAvailableShops() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "G", new BigDecimal(50)));
        expected.add(new Article("a", "F", new BigDecimal(100)));
        expected.add(new Article("a", "M", new BigDecimal(200)));

        assertTrue(expected.equals(shoppinator.search("a")));
    }

    @Test
    void CA3_multipleShopsAreSelectedShouldSearchInMultipleShops() {
        expected = new ArrayList<>();
        expected.add(new Article("a", "G", new BigDecimal(50)));
        expected.add(new Article("a", "F", new BigDecimal(100)));

        shoppinator.setShops("F", "G");
        assertTrue(expected.equals(shoppinator.search("a")));
    }

    @Test
    void CA4_invalidShopsIsSelectedShouldSearchInDefaultShops() {
        expected = new ArrayList<>();

        shoppinator.setShops("J");
        assertTrue(expected.equals(shoppinator.search("a")));
    }

}