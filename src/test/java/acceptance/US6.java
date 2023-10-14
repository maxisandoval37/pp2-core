package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.TestUtils.getTestParams;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.interfaces.ShoppinatorFacade;
import shoppinator.core.model.Product;
import stubs.ShoppinatorNonRefreshableFacadeTestImpl;
import stubs.ShoppinatorRefreshableFacadeTestImpl;

class US6 {

    private Shoppinator shoppinator;
    private List<Product> productsToRetrieve;
    private List<Product> productsRetrieved;
    private final String aProduct = "a";

    private final String path = "fake-path/";

    public void CA1_setUp() {
        ShoppinatorFacade shoppinatorFacade = new ShoppinatorRefreshableFacadeTestImpl(aProduct);
        shoppinator = new Shoppinator(shoppinatorFacade);

        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac", "ad");
    }

    @Test
    void CA1_shouldUpdateProductListWhenSearchIsRefreshed() throws FileNotFoundException {
        CA1_setUp();
        String[] testParams = getTestParams(path, aProduct);

        shoppinator.search(testParams);
        List<Product> productsBeforeUpdate = new ArrayList<>(shoppinator.getProductList());

        shoppinator.refresh();
        List<Product> productsAfterUpdate = new ArrayList<>(shoppinator.getProductList());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertNotEquals(productsBeforeUpdate.size(), productsAfterUpdate.size());
    }

    void CA2_setUp() {
        ShoppinatorFacade shoppinatorFacade = new ShoppinatorNonRefreshableFacadeTestImpl(aProduct);
        shoppinator = new Shoppinator(shoppinatorFacade);

        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac");
    }

    @Test
    void CA2_shouldNOTUpdateProductListWhenSearchIsRefreshed() throws FileNotFoundException {
        CA2_setUp();
        String[] testParams = getTestParams(path, aProduct);

        shoppinator.search(testParams);
        List<Product> productsBeforeUpdate = new ArrayList<>(shoppinator.getProductList());

        shoppinator.refresh();
        List<Product> productsAfterUpdate = new ArrayList<>(shoppinator.getProductList());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertEquals(productsBeforeUpdate.size(), productsAfterUpdate.size());
    }

    private List<Product> getTestProducts(String... productNames) {
        List<Product> products = new ArrayList<>();

        for (String productName : productNames) {
            products.add(new Product(productName, "", null));
        }

        return products;
    }

}
