package shoppinator.core.factory;

import javax.swing.text.html.parser.Element;
import shoppinator.core.model.Product;

public class ProductFactory {

    ProductLoader productLoader;

    public ProductFactory(ProductLoader productLoader) {
        this.productLoader = productLoader;
    }

    public Product create(Element element) {
        return productLoader.create(element);
    }
}
