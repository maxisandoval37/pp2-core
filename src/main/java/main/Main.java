package main;

import java.io.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import shoppinator.core.Shoppinator;

@Slf4j
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        logAsciiArt();

        Shoppinator shoppinator = new Shoppinator();
        shoppinator.init("plugins/default/");

        log.info("Featured products: {}", shoppinator.getProductList());

        shoppinator.search("plugins/availables/", "televisor", "1000", "10", "garbarino", "fravega");

        log.info("Products result: {}", shoppinator.getProductList());
    }

    public static void logAsciiArt() {
        System.out.println();
        System.out.println("\u001B[31m        ______   __    __   ______   _______   _______  ______  __    __   ______  ________   ______   _______");
        System.out.println("\u001B[33m       /      \\ |  \\  |  \\ /      \\ |       \\ |       \\|      \\|  \\  |  \\ /      \\|        \\ /      \\ |       \\");
        System.out.println("\u001B[32m      |  $$$$$$\\| $$  | $$|  $$$$$$\\| $$$$$$$\\| $$$$$$$\\\\$$$$$$| $$\\ | $$|  $$$$$$\\\\$$$$$$$$|  $$$$$$\\| $$$$$$$\\");
        System.out.println("\u001B[36m      | $$___\\$$| $$__| $$| $$  | $$| $$__/ $$| $$__/ $$ | $$  | $$$\\| $$| $$__| $$  | $$   | $$  | $$| $$__| $$");
        System.out.println("\u001B[34m       \\$$    \\ | $$    $$| $$  | $$| $$    $$| $$    $$ | $$  | $$$$\\ $$| $$    $$  | $$   | $$  | $$| $$    $$");
        System.out.println("\u001B[35m      _\\$$$$$$\\| $$$$$$$$| $$  | $$| $$$$$$$\\| $$$$$$$  | $$  | $$\\$$ $$| $$$$$$$$  | $$   | $$  | $$| $$$$$$$\\");
        System.out.println("\u001B[34m     |  \\__| $$| $$  | $$| $$__/ $$| $$      | $$      _| $$_ | $$ \\$$$$| $$  | $$  | $$   | $$__/ $$| $$  | $$");
        System.out.println("\u001B[36m      \\$$    $$| $$  | $$ \\$$    $$| $$      | $$     |   $$ \\| $$  \\$$$| $$  | $$  | $$    \\$$    $$| $$  | $$");
        System.out.println("\u001B[32m       \\$$$$$$  \\$$   \\$$  \\$$$$$$  \\$$       \\$$      \\$$$$$$ \\$$   \\$$ \\$$   \\$$   \\$$     \\$$$$$$  \\$$   \\$$");
        System.out.println("\u001B[30m");
    }
}
