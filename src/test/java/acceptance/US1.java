package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.getExpectedArticles;

import entities.Article;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US1 {

    private Shoppinator shoppinator;

    private void setUp(String path) throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create(path);
    }

    @Test
    void CA1_shouldReturnProductsOrderedByPriceOnSearch_WithMultipleShops() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");
        List<Article> expectedArticle = getExpectedArticles("a", "F", "G");

        List<Article> actualArticle = shoppinator.search("a");

        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    void CA2_shouldReturnProductsOrderedByPriceOnSearch_WithASingleShop() throws FileNotFoundException {
        this.setUp("src/test/resources/simple-shop/");
        List<Article> expectedArticle = getExpectedArticles("a", "F");

        List<Article> actualArticle = shoppinator.search("a");

        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    void CA3_shouldNotReturnProductsOnSearch_WithNoShops() throws FileNotFoundException {
        this.setUp("src/test/resources/not-shops/");
        List<Article> actualArticle = shoppinator.search("a");

        assertTrue(actualArticle.isEmpty());
    }

    @Test
    void CA4_shouldNotReturnProductsOnSearch_WhenProductIsNotAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");

        List<Article> searchArticle = shoppinator.search("e");

        assertTrue(searchArticle.isEmpty());
    }

}