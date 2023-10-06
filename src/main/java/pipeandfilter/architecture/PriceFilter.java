package pipeandfilter.architecture;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.model.Product;


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
            String productsJson = in.read();
            List<Product> products = jsonToProducts(productsJson);

            List<Product> productsList = filterProductsByPriceRange(products, min, max);

            String productsFilteredJson = productsToJson(productsList);
            out.write(productsFilteredJson);
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