package acceptance;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import entities.Product;

class US3 {

    private Shoppinator shoppinator;
    private String existingProduct;
    private String nonExistingProduct;
    private String garbarinoUrl;
    private String path;
    private String shopName;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        shoppinator = new Shoppinator();
        shoppinator.init("plugins/default/");

        existingProduct = "webcam";
        nonExistingProduct = "asd";
        shopName = "garbarino";
        path = "plugins/availables";
        garbarinoUrl = "https://garbarino.com/";
    }

    @Test
    void CA1_searchExistingProductInRealShop() throws FileNotFoundException {
        String[] testParams = {path, existingProduct, "1000", "10000000", shopName};

        shoppinator.search(testParams);
        List<Product> retrievedProducts = shoppinator.getProductList();

        assertFalse(retrievedProducts.isEmpty());
        assertTrue(retrievedProducts.get(0).getName().toLowerCase().contains(existingProduct));
    }

    @Test
    void CA2_searchNonExistingProductInRealShop() throws FileNotFoundException, IllegalArgumentException {
        String[] testParams = {path, nonExistingProduct, "1000", "10000000", shopName};
        shoppinator.search(testParams);
        List<Product> retrievedProducts = shoppinator.getProductList();
        assertTrue(retrievedProducts.isEmpty());
    }

    @Test
    void CA3_searchExistingProductInMultipleRealShops() throws FileNotFoundException, IllegalArgumentException {
        String[] testParams = {path, existingProduct, "1000", "10000000", "fravega", "garbarino"};
        List<Product> retrievedProducts = shoppinator.search(testParams);

        int fravegaCount = 0;
        int garbarinoCount = 0;

        for (Product product : retrievedProducts) {
            assertTrue(product.getName().toLowerCase().contains(existingProduct));

            if (product.getPostUrl().contains("fravega")) {
                fravegaCount++;
            } else if (product.getPostUrl().contains("garbarino")) {
                garbarinoCount++;
            }
        }

        assertTrue(fravegaCount > 0);
        assertTrue(garbarinoCount > 0);
    }

    @Test
    void CA4_successfulShopConnection() throws IOException {
        int statusCode = getHttpStatus(garbarinoUrl);
        assertEquals(200, statusCode);
    }
    
    private static int getHttpStatus(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        try {
            return connection.getResponseCode();
        } finally {
            connection.disconnect();
        }
    }
}