package infrastructure;

import domain.ProductSearcher;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import models.Product;

@SuppressWarnings("deprecation")
@Slf4j
public class ConcreteProductSearcher extends Observable implements ProductSearcher {

    public List<Product> search(String productName) {
        // aca iria toda la logica de fravega y garbarino
        List<Product> productList = Collections.emptyList();

        this.sendProductList(productList);
        return productList;
    }

    private void sendProductList(List<Product> productList) {
        setChanged();
        notifyObservers(productList);
    }
}

