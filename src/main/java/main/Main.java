package main;

import shoppinator.core.factory.ShopFactory;
import shoppinator.core.factory.ShoppinatorFactory;
import service.discovery.ScrapperDiscoverer;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.Shoppinator;

@Slf4j
public class Main {

    static Shoppinator shoppinator;

    public static void main(String[] args) {

        logAsciiArt();

        ScrapperDiscoverer scrapperDiscoverer = new ScrapperDiscoverer();
        ShopFactory shopFactory = new ShopFactory();
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory(scrapperDiscoverer, shopFactory);

        shoppinator = shoppinatorFactory.create("src/main/java/main/");
    }

    public static void logAsciiArt() {
        log.info("");
        log.info("\u001B[31m _______           _______  _______ _________ _        _______ _________ _______  _______ ");
        log.info("(  ____ \\|\\     /||(  ___  )(  ____ )\\__   __/( (    /||(  ___  )\\__   __/(  ___  )(  ____ )");
        log.info("| (    \\/| )   ( || (   ) || (    )|   ) (   |  \\  ( || (   ) |   ) (   | (   ) || (    )|");
        log.info("| (_____ | (___) || |   | || (____)|   | |   |   \\ | || (___) |   | |   | |   | || (____)|");
        log.info("(_____  )|  ___  || |   | ||  _____)   | |   | (\\ \\) ||  ___  |   | |   | |   | ||     __)");
        log.info("      ) || (   ) || |   | || (         | |   | | \\   || (   ) |   | |   | |   | || (\\ (   ");
        log.info("/\\____) || )   ( || (___) || )      ___) (___| )  \\  || )   ( |   | |   | (___) || ) \\ \\__");
        log.info("\\_______)|/     \\|(_______)|/       \\_______/|/    )_)|/     \\|   )_(   (_______)|/   \\__/");
        log.info("\u001B[0m                                                      ");
        log.info("");
    }
}