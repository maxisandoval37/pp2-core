package acceptance;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtils.getTestSearchParams;

import entities.Result;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US3 {

    private Shoppinator shoppinator;
    private String existingProduct;
    private String nonExistingProduct;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/multiple-shops/");

        existingProduct = "webcam";
        nonExistingProduct = "e";
    }

    @Test
    void CA1_searchExistingProductInRealShop() {
        shoppinator.search(getTestSearchParams(existingProduct));
        List<Result> retrievedProducts = shoppinator.getSearchResult();

        assertFalse(retrievedProducts.isEmpty());
        assertTrue(retrievedProducts.get(0).getName().toLowerCase().contains(existingProduct));
    }

    @Test
    void CA2_searchNonExistingProductInRealShop() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams(nonExistingProduct));
        assertTrue(retrievedProducts.isEmpty());
    }

    @Test
    void CA3_searchExistingProductInMultipleRealShops() {
        List<Result> retrievedProducts = shoppinator.search(getTestSearchParams(existingProduct));

        assertTrue(retrievedProducts.stream().allMatch(result ->
            result.getShopName().toLowerCase().contains("f") ||
            result.getShopName().toLowerCase().contains("g")));
    }

}