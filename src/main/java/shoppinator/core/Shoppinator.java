package shoppinator.core;

import entities.Article;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import entities.Shop;
import lombok.Getter;
import service.assembly.ArticlesAssembler;

@SuppressWarnings("deprecation")
public class Shoppinator extends Observable {

    @Getter
    private Set<Shop> shops;
    private final ArticlesAssembler articlesAssembler;

    public Shoppinator(Set<Shop> shops) {
        this.shops = shops;
        this.articlesAssembler = new ArticlesAssembler();
    }

    public List<Article> search(String productName) {
        List<Article> articles = new ArrayList<>();

        if (shops.isEmpty()) {
            return articles;
        }

        for (Shop shop : this.shops) {
            Map<String, BigDecimal> product = shop.search(productName);
            articles.addAll(articlesAssembler.assembly(product, shop.getName()));
        }

        articles.sort(Comparator.comparing(Article::getPrice));
        return articles;
    }

}
