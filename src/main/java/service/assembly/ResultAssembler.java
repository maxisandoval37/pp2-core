package service.assembly;

import entities.Product;
import entities.Result;
import entities.ProductPresentation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ResultAssembler {

    public List<Result> assembly(Set<Product> products) {
        List<Result> result = new ArrayList<>();
        for (Product product : products) {
            result.add(this.toResult(product));
        }
        result.sort(Comparator.comparing(Result::getPrice));

        return result;
    }

    private Result toResult(Product product) {
        ProductPresentation presentation = product.getProductPresentation();

        return new Result(product.getName(), product.getShopName(), presentation.getPrice(), presentation.getPostUrl(),
                   presentation.getProductImageUrl());
    }
}

