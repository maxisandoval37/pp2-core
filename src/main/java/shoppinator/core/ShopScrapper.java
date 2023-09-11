package shoppinator.core;

import shoppinator.core.factory.ProductFactory;
import shoppinator.core.interfaces.Scrapper;
import shoppinator.core.interfaces.Shop;
import java.util.List;
import javax.swing.text.html.parser.Element;
import shoppinator.core.model.Product;

public class ShopScrapper extends Shop {

    Scrapper scrapper;
    ProductFactory productFactory;

    public ShopScrapper(Scrapper scrapper) {
        this.scrapper = scrapper;
    }

    @Override
    public List<Product> search(String productName) {
        Element element = scrapper.scrap(productName);
        Product product = productFactory.create(element);
        this.addProduct(product);

        return this.getProducts();
    }
}
