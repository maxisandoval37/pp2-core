package main;

import models.Product;
import lombok.extern.slf4j.Slf4j;
import service.ProductSearcher;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        // Ejemplo, cuando esto esté posta debería crearlo la UI y pasarle
        // las url de las tiendas que los va a tener en un aplication.properties
        String[] shopsUrls = new String[2];
        shopsUrls[0] = "https://www.fravega.com/l/?keyword={}&sorting=LOWEST_SALE_PRICE&page=";
        shopsUrls[1] = "https://www.google.com/search?q={}";

        log.info(" _______           _______  _______ _________ _        _______ _________ _______  _______ ");
        log.info("(  ____ \\|\\     /||(  ___  )(  ____ )\\__   __/( (    /||(  ___  )\\__   __/(  ___  )(  ____ )");
        log.info("| (    \\/| )   ( || (   ) || (    )|   ) (   |  \\  ( || (   ) |   ) (   | (   ) || (    )|");
        log.info("| (_____ | (___) || |   | || (____)|   | |   |   \\ | || (___) |   | |   | |   | || (____)|");
        log.info("(_____  )|  ___  || |   | ||  _____)   | |   | (\\ \\) ||  ___  |   | |   | |   | ||     __)");
        log.info("      ) || (   ) || |   | || (         | |   | | \\   || (   ) |   | |   | |   | || (\\ (   ");
        log.info("/\\____) || )   ( || (___) || )      ___) (___| )  \\  || )   ( |   | |   | (___) || ) \\ \\__");
        log.info("\\_______)|/     \\|(_______)|/       \\_______/|/    )_)|/     \\|   )_(   (_______)|/   \\__/");
        log.info("                                                      ");

        ProductSearcher productSearcher = new ProductSearcher(shopsUrls);
        List<Product> productList = productSearcher.searchProducts("plancha a vapor");

        for (Product product : productList) {
            log.info("Nombre: " + product.getName());
            log.info("Precio: $" + product.getProductPresentation().getPrice());
            log.info("URL del producto: " + product.getProductPresentation().getShopProduct().getPostUrl());
            log.info("URL de la imagen: " + product.getProductPresentation().getProductImage().getImgUrl());
        }
    }
}