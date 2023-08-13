package utils;

public class PriceUtils {

    public static Float convertPriceStrToFloat(String priceText) {
        return Float.parseFloat(priceText
                .replace("$", "")
                .replace(".", "")
                .replace(",", "."));
    }
}