package shoppinator.core;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import entities.Shop;
import entities.Product;
import entities.criteria.SearchCriteria;

public interface ShoppinatorFacade {

    List<Product> searchProductsInShops(SearchCriteria criteria) throws FileNotFoundException;

    List<Product> getCurrentProductList();

    Set<Shop> getShops();

    void subscribe(Object observer);

}
