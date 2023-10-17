package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.TestUtils.getTestParams;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.ShoppinatorFacade;
import entities.Product;
import stubs.ShoppinatorNonRefreshableFacadeTestImpl;
import stubs.ShoppinatorRefreshableFacadeTestImpl;

class US6 {

    private Shoppinator shoppinator;
    private List<Product> productsToRetrieve;
    private List<Product> productsRetrieved;
    private String productName;

    private String path;

    @BeforeEach
    public void setUp() {
        productName = "a";
        path = "fake-path/";
    }

    public void CA1_setUp() {
        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac", "ad");

        ShoppinatorFacade shoppinatorFacade = new ShoppinatorRefreshableFacadeTestImpl(productName, productsToRetrieve, productsRetrieved);
        shoppinator = new Shoppinator(shoppinatorFacade);
    }

    @Test
    void CA1_shouldUpdateProductListWhenSearchIsRefreshed() throws FileNotFoundException {
        CA1_setUp();
        String[] testParams = getTestParams(path, productName);

        shoppinator.search(testParams);
        List<Product> productsBeforeUpdate = new ArrayList<>(shoppinator.getProductList());

        shoppinator.refresh();
        List<Product> productsAfterUpdate = new ArrayList<>(shoppinator.getProductList());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertNotEquals(productsBeforeUpdate, productsAfterUpdate);
    }

    void CA2_setUp() {
        ShoppinatorFacade shoppinatorFacade = new ShoppinatorNonRefreshableFacadeTestImpl(productName);
        shoppinator = new Shoppinator(shoppinatorFacade);

        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac");
    }

    @Test
    void CA2_shouldNOTUpdateProductListWhenSearchIsRefreshed() throws FileNotFoundException {
        CA2_setUp();
        String[] testParams = getTestParams(path, productName);

        shoppinator.search(testParams);
        List<Product> productsBeforeUpdate = new ArrayList<>(shoppinator.getProductList());

        shoppinator.refresh();
        List<Product> productsAfterUpdate = new ArrayList<>(shoppinator.getProductList());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertEquals(productsBeforeUpdate, productsAfterUpdate);
    }

    private List<Product> getTestProducts(String... productNames) {
        List<Product> products = new ArrayList<>();

        for (String productName : productNames) {
            products.add(new Product(productName, "", null));
        }

        return products;
    }

}
