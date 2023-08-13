import models.Product;
import lombok.extern.slf4j.Slf4j;
import service.ProductScraper;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Bienvenido al Comparador de Precios!");

        ProductScraper productScraper = new ProductScraper();
        List<Product> productList = productScraper.scrapeProducts("boca");

        for (Product product : productList) {
            System.out.println("Nombre: " + product.getName());
            System.out.println("Precio: $" + product.getPrice());
            System.out.println("Tienda: " + product.getStore());
            System.out.println("URL del producto: " + product.getPostUrl());
            System.out.println("URL de la imagen: " + product.getImgUrl());
            System.out.println();
        }
    }
}