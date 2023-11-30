package shoppinator.core;

import entities.Article;
import entities.Shop;
import entities.criteria.Searchable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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


    public ShoppinatorCore(Set<Shop> shops) {
        this.setShops(shops);
        this.articlesAssembler = new ArticlesAssembler();
    }

    @Override
    public List<Article> search(String productName) {
        List<Article> articles = new ArrayList<>();

        if (this.shopsForSearching.isEmpty()) {
            return articles;
        }

        productName = productName.trim();
        for (Shop shop : this.shopsForSearching) {
            Map<String, BigDecimal> products = shop.search(productName);
            articles.addAll(articlesAssembler.assembly(products, shop.getName()));
        }

        articles.sort(Comparator.comparing(Article::getPrice));
        return articles;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable o, Object products) {
        Shop shop = (Shop) o;
        List<Article> articles = new ArrayList<>(
            articlesAssembler.assembly((Map<String, BigDecimal>) products, shop.getName()));

        setChanged();
        super.notifyObservers(articles);
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
            if (!this.shopsToLoad.containsKey(shopName)) {
                continue;
            }
            selectedShops.add(this.shopsToLoad.get(shopName));
        }
        this.shopsForSearching = selectedShops;
    }
}
