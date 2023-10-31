package entities.filtering;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import entities.Product;


@Slf4j
public class PriceFilter extends Filter {

    private final Long min;
    private final Long max;

    public PriceFilter(Long minPrice, Long maxPrice) {
        this.min = minPrice;
        this.max = maxPrice;
    }

    @Override
    public void run() {
        try {
            List<Product> products = in.read();

            List<Product> productsList = filterProductsByPriceRange(products, min, max);

            out.write(productsList);
            out.close();
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Product> filterProductsByPriceRange(List<Product> products, double min, double max) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            double price = product.getProductPresentation().getPrice();
            if (price >= min && price <= max) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}