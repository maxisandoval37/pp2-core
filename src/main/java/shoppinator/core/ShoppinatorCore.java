package shoppinator.core;

import entities.Article;
import entities.Shop;
import entities.criteria.Searchable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import service.assembly.ArticlesAssembler;

@SuppressWarnings("deprecation")
@Getter
public class ShoppinatorCore extends Searchable implements Observer {

    @Getter
    private Set<Shop> shopsForSearching;

    private Map<String, Shop> shopsToLoad;

    private final ArticlesAssembler articlesAssembler;

    private List<Article> articles;

    private Set<Map<String, BigDecimal>> domainProducts;

    public ShoppinatorCore(Set<Shop> shops) {
        this.setShops(shops);
        this.domainProducts = new HashSet<>();
        this.articles = new ArrayList<>();
        this.articlesAssembler = new ArticlesAssembler();
    }

    @Override
    public List<Article> search(String query) {
        this.domainProducts.clear();
        this.articles.clear();

        for (Shop shop : this.shopsForSearching) {
            shop.search(query);
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

    public void setShops(Set<Shop> shops) {
        this.shopsForSearching = shops;
        this.shopsToLoad = shops.stream()
            .collect(Collectors.toMap(Shop::getName, shop -> shop));
        this.addObservers();
    }

    private void addObservers() {
        for (Shop shop : this.shopsForSearching) {
            shop.addObserver(this);
        }
    }

    public Set<String> getShopNames() {
        return this.shopsToLoad.values().stream()
            .map(Shop::getName)
            .collect(Collectors.toSet());
    }

    public void setShops(String... shopNames) {
        Set<Shop> selectedShops = new HashSet<>();
        for (String shopName : shopNames) {
            selectedShops.add(this.shopsToLoad.get(shopName));
        }
        this.shopsForSearching = selectedShops;
    }
}
