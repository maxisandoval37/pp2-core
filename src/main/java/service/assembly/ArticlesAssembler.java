package service.assembly;

import entities.Article;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArticlesAssembler {

    public List<Article> assembly(Set<Map<String, BigDecimal>> products, String shopName) {
        List<Article> articles = new ArrayList<>();

        for (Map<String, BigDecimal> product : products) {
            for (Map.Entry<String, BigDecimal> entry : product.entrySet()) {
                articles.add(new Article(entry.getKey(), shopName, entry.getValue()));
            }
        }

        return articles;
    }
}

