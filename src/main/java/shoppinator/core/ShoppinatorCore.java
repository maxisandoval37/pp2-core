package shoppinator.core;

import entities.Result;
import entities.Shop;
import entities.criteria.SearchCriteria;
import java.util.List;
import java.util.Observer;
import java.util.Set;

public interface ShoppinatorCore {

    List<Result> search(SearchCriteria criteria);

    List<Result> getSearchResult();

    Set<Shop> getShops();

    void setShops(Set<Shop> shops);

    void addObserver(Observer observer);
}
