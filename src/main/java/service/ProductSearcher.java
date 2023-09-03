package service;

import extensions.FravegaShop;
import extensions.GarbarinoShop;
import interfaces.Shop;
import models.Product;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("deprecation")
@Slf4j
public class ProductSearcher extends Observable {

    private final Set<Shop> shops;

    public ProductSearcher() {
        this.shops = new HashSet<>();
        shops.add(new FravegaShop());
        shops.add(new GarbarinoShop());
    }

    public List<Product> products(String productName) throws InterruptedException, ExecutionException {
        List<Product> allProductList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(shops.size());

        for (Shop shop : shops) {
            Future<List<Product>> productListF = executorService.submit(
                () -> shop.products(productName));
            allProductList.addAll(productListF.get());
        }

        executorService.shutdown();

        allProductList.sort(Comparator.comparing(product -> product.getProductPresentation().getPrice()));

        this.sendProductList(allProductList);
        return allProductList;
    }

    private void sendProductList(List<Product> productList) {
        setChanged();
        notifyObservers(productList);
    }
}