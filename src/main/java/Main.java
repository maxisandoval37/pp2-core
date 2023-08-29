import models.Product;
import lombok.extern.slf4j.Slf4j;
import service.ProductScraper;
import java.util.List;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info(" _______           _______  _______ _________ _        _______ _________ _______  _______ ");
        log.info("(  ____ \\|\\     /||(  ___  )(  ____ )\\__   __/( (    /||(  ___  )\\__   __/(  ___  )(  ____ )");
        log.info("| (    \\/| )   ( || (   ) || (    )|   ) (   |  \\  ( || (   ) |   ) (   | (   ) || (    )|");
        log.info("| (_____ | (___) || |   | || (____)|   | |   |   \\ | || (___) |   | |   | |   | || (____)|");
        log.info("(_____  )|  ___  || |   | ||  _____)   | |   | (\\ \\) ||  ___  |   | |   | |   | ||     __)");
        log.info("      ) || (   ) || |   | || (         | |   | | \\   || (   ) |   | |   | |   | || (\\ (   ");
        log.info("/\\____) || )   ( || (___) || )      ___) (___| )  \\  || )   ( |   | |   | (___) || ) \\ \\__");
        log.info("\\_______)|/     \\|(_______)|/       \\_______/|/    )_)|/     \\|   )_(   (_______)|/   \\__/");
        log.info("                                                      ");

        ProductScraper productScraper = new ProductScraper();
        List<Product> productList = productScraper.scrapeProducts("plancha a vapor");

        for (Product product : productList) {
            System.out.println("Nombre: " + product.getName());
            System.out.println("Precio: $" + product.getPrice());
            System.out.println("URL del producto: " + product.getPostUrl());
            System.out.println("URL de la imagen: " + product.getImgUrl());
            System.out.println();
        }
    }
}