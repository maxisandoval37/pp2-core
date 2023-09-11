package shoppinator.core.factory;

import javax.swing.text.html.parser.Element;
import shoppinator.core.model.Product;

public class ProductLoader {

    public Product create(Element element) {
        return this.load(element);
    }

    // No entiendo esto, no se supone que el factory es para crear objetos?
    private Product load(Element element) {
        return null;
    }
}
