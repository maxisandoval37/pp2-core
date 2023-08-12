import Models.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Bienvenido al Comparador de Precios!");

        Product product = new Product();
        product.setName("Cocina");
        product.setPrice(200000.0);
        product.setStore("Mi Store");
        product.setPostUrl("https://www.google.com/");
        product.setImgUrl("https://google.com/search?q=cocina");

        log.info(product.toString());
    }
}