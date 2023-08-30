import models.Product;
import lombok.extern.slf4j.Slf4j;
import service.ProductScraper;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {

        log.info("");
        log.info(" _______           _______  _______ _________ _        _______ _________ _______  _______ ");
        log.info("(  ____ \\|\\     /||(  ___  )(  ____ )\\__   __/( (    /||(  ___  )\\__   __/(  ___  )(  ____ )");
        log.info("| (    \\/| )   ( || (   ) || (    )|   ) (   |  \\  ( || (   ) |   ) (   | (   ) || (    )|");
        log.info("| (_____ | (___) || |   | || (____)|   | |   |   \\ | || (___) |   | |   | |   | || (____)|");
        log.info("(_____  )|  ___  || |   | ||  _____)   | |   | (\\ \\) ||  ___  |   | |   | |   | ||     __)");
        log.info("      ) || (   ) || |   | || (         | |   | | \\   || (   ) |   | |   | |   | || (\\ (   ");
        log.info("/\\____) || )   ( || (___) || )      ___) (___| )  \\  || )   ( |   | |   | (___) || ) \\ \\__");
        log.info("\\_______)|/     \\|(_______)|/       \\_______/|/    )_)|/     \\|   )_(   (_______)|/   \\__/");
        log.info("                                                      ");
        log.info("");

        ProductScraper productScraper = new ProductScraper();
        List<Product> productList = productScraper.scrapeProducts("plancha a vapor");

        for (Product product : productList) {
            log.info("Nombre: " + product.getName());
            log.info("Precio: $" + product.getPrice());
            log.info("URL del producto: " + product.getPostUrl());
            log.info("URL de la imagen: " + product.getImgUrl());
            log.info("                         ------------");
        }
    }
}