package shoppinator.core;

import java.io.FileNotFoundException;
import java.util.List;
import shoppinator.core.facade.ShoppinatorFacade;
import shoppinator.core.model.Product;

public class Shoppinator {

    ShoppinatorFacade facade;

    public Shoppinator() {
        facade = new ShoppinatorFacade();
    }

    public void init(String path) throws FileNotFoundException {
        facade.init(path);
    }

    public void search(String... params) throws FileNotFoundException {
        facade.search(params);
    }

    public List<Product> getProductList() {
        return facade.getProductList();
    }

}
