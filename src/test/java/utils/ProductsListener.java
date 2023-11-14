package utils;


import entities.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ProductsListener implements Observer {

    public List<Article> products;

    public ProductsListener() {
        this.products = new ArrayList<>();
    }

    @Override
    public void update(Observable o, Object product) {

        this.products.addAll((List<Article>) product);
    }
}
