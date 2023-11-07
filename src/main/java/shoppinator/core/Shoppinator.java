package shoppinator.core;

import entities.Result;
import entities.criteria.Searchable;
import java.util.List;
import java.util.Observer;
import java.util.Set;
import entities.Shop;
import java.util.stream.Collectors;

public class Shoppinator {

    private final Searchable searcher;
    private final Set<Shop> shops;

    public Shoppinator(Searchable searcher, Set<Shop> shops) {
        this.shops = shops;
        this.searcher = searcher;
    }

    public List<Result> search(String params) throws IllegalArgumentException {
        return searcher.search(params);
    }

    public Set<String> getShopNames() {
        return this.shops.stream()
            .map(Shop::getName)
            .collect(Collectors.toSet());
    }

    @SuppressWarnings("deprecation")
    public void addObserver(Observer observer) {
        this.searcher.addObserver(observer);
    }

}
