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

        log.info("Products on inicialization: {}", shoppinator.getProductList());

        shoppinator.search("plugins/availables", "webcam", "1000", "100000", "garbarino");

        log.info("Products after search:");
        shoppinator.getProductList().forEach(product -> log.info("Product: {}", product));

        shoppinator.refresh();

        log.info("Products after refresh:");
        shoppinator.getProductList().forEach(product -> log.info("Product: {}", product));
    }

    private static void logAsciiArt() {
        String[] asciiLines = {
            "        ______   __    __   ______   _______   _______  ______  __    __   ______  ________   ______   _______",
            "       /      \\ |  \\  |  \\ /      \\ |       \\ |       \\|      \\|  \\  |  \\ /      \\|        \\ /      \\ |       \\",
            "      |  $$$$$$\\| $$  | $$|  $$$$$$\\| $$$$$$$\\| $$$$$$$\\\\$$$$$$| $$\\ | $$|  $$$$$$\\$$$$$$$$|  $$$$$$\\| $$$$$$$\\",
            "      | $$___\\$$| $$__| $$| $$  | $$| $$__/ $$| $$__/ $$ | $$  | $$$\\| $$| $$__| $$  | $$   | $$  | $$| $$__| $$",
            "       \\$$    \\ | $$    $$| $$  | $$| $$    $$| $$    $$ | $$  | $$$$\\ $$| $$    $$  | $$   | $$  | $$| $$    $$",
            "      _\\$$$$$$\\| $$$$$$$$| $$  | $$| $$$$$$$\\| $$$$$$$  | $$  | $$\\$$ $$| $$$$$$$$  | $$   | $$  | $$| $$$$$$$\\",
            "     |  \\__| $$| $$  | $$| $$__/ $$| $$      | $$      _| $$_ | $$ \\$$$$| $$  | $$  | $$   | $$__/ $$| $$  | $$",
            "      \\$$    $$| $$  | $$ \\$$    $$| $$      | $$     |   $$ \\| $$  \\$$$| $$  | $$  | $$    \\$$    $$| $$  | $$",
            "       \\$$$$$$  \\$$   \\$$  \\$$$$$$  \\$$       \\$$      \\$$$$$$ \\$$   \\$$ \\$$   \\$$   \\$$     \\$$$$$$  \\$$   \\$$"
        };

        String[] colorCodes = {
            "\u001B[31m",
            "\u001B[33m",
            "\u001B[32m",
            "\u001B[36m",
            "\u001B[34m",
            "\u001B[35m",
            "\u001B[34m",
            "\u001B[36m",
            "\u001B[32m",
            "\u001B[30m"
        };

        for (int i = 0; i < asciiLines.length; i++) {
            System.out.println(colorCodes[i] + asciiLines[i]);
        }
    }

}