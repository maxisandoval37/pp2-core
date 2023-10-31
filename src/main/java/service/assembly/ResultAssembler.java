package service.assembly;

import entities.Product;
import entities.Result;
import entities.ProductPresentation;
import entities.criteria.FilterCriteria;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import service.filtering.ProductsFilterer;

public class ResultAssembler {

    ProductsFilterer productsFilterer;

    public ResultAssembler() {
        this.productsFilterer = new ProductsFilterer();
    }

    public List<Result> assembly(Set<Product> products, FilterCriteria filterCriteria) {
        List<Product> filtered = productsFilterer.filter(filterCriteria, products);
        List<Result> result = new ArrayList<>();

        for (Product product : filtered) {
            result.add(this.toResult(product));
        }

        return result;
    }

    private Result toResult(Product product) {
        ProductPresentation presentation = product.getProductPresentation();

        return new Result(product.getName(), product.getShopName(), presentation.getPrice(), presentation.getPostUrl(),
            presentation.getProductImageUrl());
    }
}

