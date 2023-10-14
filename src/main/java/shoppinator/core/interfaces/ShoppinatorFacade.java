package shoppinator.core.interfaces;

import java.io.FileNotFoundException;
import java.util.List;
import shoppinator.core.model.Product;
import shoppinator.core.model.criteria.SearchCriteria;

public interface ShoppinatorFacade {

    List<Product> searchProductsInShops(SearchCriteria criteria) throws FileNotFoundException;

    List<Product> getCurrentProductList();

    void subscribe(Object observer);
}
