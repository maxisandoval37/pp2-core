package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.TestUtils.getTestSearchParams;

import entities.Result;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.ShoppinatorCore;
import stubs.ShoppinatorRefreshableStub;
import stubs.ShopsContainerStub;

class US6 {

    private Shoppinator shoppinator;
    private List<Result> productsToRetrieve;
    private List<Result> productsRetrieved;
    private String productName;


    @BeforeEach
    public void setUp() {
        productName = "a";
    }

    public void CA1_setUp() {
        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac", "ad");

        ShoppinatorCore shoppinatorCore = new ShoppinatorRefreshableStub(productName, productsToRetrieve,
            productsRetrieved);
        shoppinator = new Shoppinator(shoppinatorCore, new ShopsContainerStub());
    }

    @Test
    void CA1_shouldUpdateProductListWhenSearchIsRefreshed() throws FileNotFoundException {
        CA1_setUp();

        List<Result> productsBeforeUpdate = new ArrayList<>(shoppinator.search(getTestSearchParams(productName)));
        List<Result> productsAfterUpdate = new ArrayList<>(shoppinator.refresh());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertNotEquals(productsBeforeUpdate, productsAfterUpdate);
    }

    void CA2_setUp() {
        productsToRetrieve = getTestProducts("aa", "ab", "ac");
        productsRetrieved = getTestProducts("aa", "ab", "ac");

        ShoppinatorCore shoppinatorCore = new ShoppinatorRefreshableStub(productName, productsToRetrieve,
            productsRetrieved);
        shoppinator = new Shoppinator(shoppinatorCore, new ShopsContainerStub());
    }

    @Test
    void CA2_shouldNOTUpdateProductListWhenSearchIsRefreshed() {
        CA2_setUp();

        List<Result> productsBeforeUpdate = new ArrayList<>(shoppinator.search(getTestSearchParams(productName)));
        List<Result> productsAfterUpdate = new ArrayList<>(shoppinator.refresh());

        assertEquals(productsBeforeUpdate, productsToRetrieve);
        assertEquals(productsAfterUpdate, productsRetrieved);
        assertEquals(productsBeforeUpdate, productsAfterUpdate);
    }

    private List<Result> getTestProducts(String... productNames) {
        List<Result> products = new ArrayList<>();

        for (String productName : productNames) {
            products.add(new Result(productName, "Fake Shop", 1L, "http://example.com", "http://example.com"));
        }

        return products;
    }

}
