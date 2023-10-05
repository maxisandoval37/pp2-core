package pipeandfilter;


import com.fasterxml.jackson.core.JsonProcessingException;
import shoppinator.core.model.Product;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;


public class PriceFilter extends Filter {

    private final Integer min;
    private final Integer max;
    public PriceFilter(Integer priceMin, Integer priceMax) {
        this.min = priceMin;
        this.max = priceMax;
    }

    @Override
    public void run() {

        List<Product> products = new ArrayList<>();
        List<Product> productsList = new ArrayList<>();
        try {

                String productsJson = in.read();
                List<Product> productsConverted = jsonToProducts(productsJson);
                products.addAll(productsConverted);
            } catch (EOFException ex) {
                throw new RuntimeException(ex);
        }

        for (Product product : products) {
            if (product.getProductPresentation().getPrice() >= min && product.getProductPresentation().getPrice() <= max) {
                productsList.add(product);
            }
        }

        try {
            String productsFilteredJson = productsToJson(productsList);
            out.write(productsFilteredJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }
}