package service.assembly;

import entities.Article;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticlesAssembler {

    public List<Article> assembly(Map<String, BigDecimal> products, String shopName) {
        List<Article> articles = new ArrayList<>();

        for (Map.Entry<String, BigDecimal> product : products.entrySet()) {
            Article article = new Article();
            article.setName(product.getKey());
            article.setPrice(product.getValue());
            article.setShop(shopName);
            articles.add(article);
        }

        return articles;
    }
}

