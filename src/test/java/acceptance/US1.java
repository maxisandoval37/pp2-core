package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
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
        this.setUp("src/test/resources/multiple-shops/");
        List<Article> expectedArticle = this.getExpectedArticle("a", "F", "G");

        List<Article> actualArticle = shoppinator.search("a");

        assertEquals(expectedArticle, actualArticle);
    }

    @Test
    void CA2_shouldReturnProductsOrderedByPriceOnSearch_WithASingleShop() throws FileNotFoundException {
        this.setUp("src/test/resources/single-shop/");
        List<Article> expectedArticle = this.getExpectedArticle("a", "F");

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
    void CA2_shouldNotReturnProductsOnSearch_WhenProductIsNotAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");

        List<Article> searchArticle = shoppinator.search("e");

        assertTrue(searchArticle.isEmpty());
    }

    private List<Article> getExpectedArticle(String productName, String... shopNames) {
        List<Article> Articles = new ArrayList<>();
        BigDecimal initialPrice = new BigDecimal(100);

        for (String shopName : shopNames) {
            Article Article = new Article(productName, shopName, initialPrice);
            Articles.add(Article);

            initialPrice = initialPrice.add(new BigDecimal(100));
        }

        return Articles;
    }

}