package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        this.setUp("src/test/resources/US1/");
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new Article("a", "F", new BigDecimal(100)));
        expectedArticles.add(new Article("a", "G", new BigDecimal(200)));
        expectedArticles.add(new Article("a", "H", new BigDecimal(200)));

        List<Article> anotherExpectedArticles = new ArrayList<>();
        anotherExpectedArticles.add(new Article("a", "F", new BigDecimal(100)));
        anotherExpectedArticles.add(new Article("a", "H", new BigDecimal(200)));
        anotherExpectedArticles.add(new Article("a", "G", new BigDecimal(200)));

        List<Article> actualArticle = shoppinator.search("a");

        assertTrue(expectedArticles.equals(actualArticle) || anotherExpectedArticles.equals(actualArticle));
    }

    @Test
    void CA2_shouldReturnProductsOrderedByPriceOnSearch_WithASingleShop() throws FileNotFoundException {
        this.setUp("src/test/resources/simple-shop/");
        List<Article> expectedArticle = new ArrayList<>();
        expectedArticle.add(new Article("a", "F", new BigDecimal(100)));

        List<Article> actualArticle = shoppinator.search("a");

        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    void CA3_shouldNotReturnProductsOnSearch_WithNoShops() throws FileNotFoundException {
        this.setUp("src/test/resources/no-shops/");
        List<Article> actualArticle = shoppinator.search("a");

        assertTrue(actualArticle.isEmpty());
    }

    @Test
    void CA4_shouldNotReturnProductsOnSearch_WhenProductIsNotAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/US1/");

        List<Article> searchArticle = shoppinator.search("e");

        assertTrue(searchArticle.isEmpty());
    }

    @Test
    void CA5_shouldReturnOneProductOnSearch_WhenProductIsOnlyAvailableInOneShop() throws FileNotFoundException {
        this.setUp("src/test/resources/US1/");
        List<Article> expectedArticle = new ArrayList<>();
        expectedArticle.add(new Article("b", "I", new BigDecimal(300)));

        List<Article> searchArticle = shoppinator.search("b");

        assertEquals(expectedArticle, searchArticle);
    }

}