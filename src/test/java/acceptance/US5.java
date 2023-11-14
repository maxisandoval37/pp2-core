package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.getExpectedArticles;

import entities.Article;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US5 {

    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/multiple-shops/");
    }

    @Test
    void CA1_oneShopIsSelectedShouldSearchInOneShop() {
        List<Article> retrievedProducts = getExpectedArticles("webcam", "F");

        shoppinator.setShops("F");
        shoppinator.search("webcam");

        assertTrue(retrievedProducts.stream().allMatch(x -> x.getShop().equals("F")));
    }

    @Test
    void CA2_noShopsAreSelectedShouldSearchInAllAvailableShops() {
        List<Article> retrievedProducts = getExpectedArticles("webcam", "F", "G");

        shoppinator.search("webcam");

        assertTrue(retrievedProducts.stream().anyMatch(x -> x.getShop().equals("F") || x.getShop().equals("G")));
    }

    @Test
    void CA3_multipleShopsAreSelectedShouldSearchInMultipleShops() {
        List<Article> retrievedProducts = getExpectedArticles("webcam", "F", "G");

        shoppinator.setShops("F", "G");
        shoppinator.search("webcam");

        assertTrue(retrievedProducts.stream().anyMatch(x -> x.getShop().equals("F") || x.getShop().equals("G")));
    }

}