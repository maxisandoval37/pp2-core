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
    private final ShoppinatorCore core;

    public Shoppinator(Searchable searcher, ShoppinatorCore core) {
        this.core = core;
        this.searcher = searcher;
    }

    public List<Result> search(String params) throws IllegalArgumentException {
        List<Result> results = searcher.search(params);
        this.core.notifyObservers(results);
        return results;
    }

    public Set<String> getShopNames() {
        return this.core.getShops().stream()
            .map(Shop::getName)
            .collect(Collectors.toSet());
    }

    @SuppressWarnings("deprecation")
    public void addObserver(Observer observer) {
        this.searcher.addObserver(observer);
    }

}
