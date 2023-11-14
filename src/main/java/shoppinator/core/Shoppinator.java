package shoppinator.core;

import entities.Article;
import entities.criteria.Searchable;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import entities.Shop;
import java.util.stream.Collectors;

public class Shoppinator {

    private final Searchable searcher;
    private final ShoppinatorCore core;

    public Shoppinator(Searchable searcher, ShoppinatorCore core) {
        this.core = core;
        this.searcher = searcher;
    }

    public List<Article> search(String query) throws IllegalArgumentException {
        return searcher.search(query);
    }

    public Set<String> getShopNames() {
        return this.core.getShopsForSearching().stream()
            .map(Shop::getName)
            .collect(Collectors.toSet());
    }

    public void setShops(String... shopNames) {
        this.core.setShops(shopNames);
    }

    @SuppressWarnings("deprecation")
    public void addObserver(Observer observer) {
        this.searcher.addObserver(observer);
    }

}
