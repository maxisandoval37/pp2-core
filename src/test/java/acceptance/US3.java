package acceptance;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.getExpectedArticles;

import entities.Article;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US3 {

    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/multiple-shops/");
    }

    @Test
    void CA1_searchExistingProductInFravegaShop() {
        shoppinator.search("mouse");
        List<Article> retrievedProducts = getExpectedArticles("mouse", "Fravega");

        assertEquals(retrievedProducts, shoppinator.search("mouse"));
    }

    @Test
    void CA2_searchNonExistingProductInRealShop() {
        List<Article> retrievedProducts = shoppinator.search("asd");
        assertTrue(retrievedProducts.isEmpty());
    }

}