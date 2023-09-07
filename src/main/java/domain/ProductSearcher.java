package domain;

import java.util.List;
import java.util.concurrent.ExecutionException;
import models.Product;

public interface ProductSearcher {

    List<Product> search(String productName) throws InterruptedException, ExecutionException;
}
