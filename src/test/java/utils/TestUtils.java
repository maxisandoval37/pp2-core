package utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public TestUtils() {
    }

    public static List<Article> getExpectedArticles(String productName, String... shopNames) {
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
