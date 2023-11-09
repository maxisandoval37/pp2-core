package shoppinator.core;

import entities.Article;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class Shoppinator extends Observable implements Observer {

    @Getter
    private Set<Shop> shops;
    private final ArticlesAssembler articlesAssembler;
    private List<Article> articles;

    public Shoppinator(Set<Shop> shops) {
        this.setShops(shops);
        this.articles = new ArrayList<>();
        this.articlesAssembler = new ArticlesAssembler();
    }

    public List<Article> search(String productName) {
        this.articles.clear();

        if (shops.isEmpty()) {
            super.notifyObservers(this.articles);
        }

        for (Shop shop : this.shops) {
            shop.search(productName);
        }

        return this.articles;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object products) {
        Shop shop = (Shop) o;
        this.articles.addAll(articlesAssembler.assembly((Set<Map<String, BigDecimal>>) products, shop.getName()));

        setChanged();
        super.notifyObservers(this.articles);
    }

    private void setShops(Set<Shop> shops) {
        this.shops = shops;
        this.addObservers();
    }

    private void addObservers() {
        for (Shop shop : this.shops) {
            shop.addObserver(this);
        }
    }
}
