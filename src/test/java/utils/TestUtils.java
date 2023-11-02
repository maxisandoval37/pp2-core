package utils;

import java.util.Map;

public class TestUtils {

    public TestUtils() {
    }

    public static String[] getTestParams(String path, String productsToSearch) {
        return new String[]{path, productsToSearch, "0", "1000"};
    }

    public static Map<String, Object> getTestSearchParams(String productName) {
        return Map.of("productName", productName, "selectedShops", new String[]{"F", "G"});
    }

    public static Map<String, Object> getTestSearchParams(String aProduct, Long minPrice, Long maxPrice) {
        return Map.of("productName", aProduct, "minPrice", minPrice, "maxPrice", maxPrice, "selectedShops",
            new String[]{"F", "G"});
    }

    public static Map<String, Object> getTestSearchParams(String webcam, String[] selectedShops) {
        return Map.of("productName", webcam, "selectedShops", selectedShops);
    }

}
