package shoppinator.core.interfaces;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

public interface ShoppinatorFacade {

    List<Product> searchProductsInShops(SearchCriteria criteria) throws FileNotFoundException;

    List<Product> getCurrentProductList();

    Set<Shop> getShops();

    void subscribe(Object observer);

}
