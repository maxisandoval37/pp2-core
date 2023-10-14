package utils;

public class TestUtils {

    public TestUtils() {}

    public static String[] getTestParams(String path, String productsToSearch) {
        return new String[]{path, productsToSearch, "0", "1000"};
    }

}
