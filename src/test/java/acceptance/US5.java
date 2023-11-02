package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestUtils.getTestSearchParams;

import entities.Shop;
import java.io.FileNotFoundException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US5 {

    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory("a");
        shoppinator = shoppinatorFactory.create("src/test/resources/multiple-shops/");
    }

    @Test
    void CA1_oneOnlineShopIsSelectedOnSearchShouldLoadOneShopInShoppinator() {
        shoppinator.search(getTestSearchParams("webcam", new String[]{"F"}));
        Set<Shop> shops = shoppinator.getShops();

        assertEquals(1, shops.size());
        for (Shop shop : shops) {
            assertEquals("F", shop.getName());
        }
    }

    @Test
    void CA2_noOnlineShopsAreSelectedOnSearchShouldThrowIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            shoppinator.search(getTestSearchParams("webcam", new String[]{}));
        });
    }

    @Test
    void CA3_multipleOnlineShopsAreSelectedOnSearchShouldLoadMultipleShopsInShoppinator() {
        shoppinator.search(getTestSearchParams("webcam", new String[]{"F", "G"}));
        Set<Shop> shops = shoppinator.getShops();

        assertEquals(2, shops.size());
    }

}