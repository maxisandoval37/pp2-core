package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Shop;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.discovery.ShopsDiscoverer;

class US2 {

    ShopsDiscoverer shopsDiscoverer;

    @BeforeEach
    public void setUp() {
        shopsDiscoverer = new ShopsDiscoverer();
    }

    @Test
    void CA1_DiscoveryOnNonExistentLocation_ShouldThrow_FileNotFoundException() {
        String nonExistingPath = "non-existent-location/";

        assertThrows(FileNotFoundException.class, () -> shopsDiscoverer.discover(nonExistingPath));
    }

    @Test
    void CA2_DiscoveryOnInvalidPath_ShouldThrow_IllegalArgumentException() {
        String invalidPath = "invalid|||format|||file|||txt";
        assertThrows(IllegalArgumentException.class, () -> shopsDiscoverer.discover(invalidPath));
    }

    @Test
    void CA3_DiscoveryOnEmptyFolder_ShouldReturn_EmptySet() throws FileNotFoundException {
        String emptyFolderPath = "src/test/resources/no-shops";

        Set<Shop> result = shopsDiscoverer.discover(emptyFolderPath);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA4_DiscoveryOnFolderWithNonShopFile_ShouldReturn_EmptySet() throws FileNotFoundException {
        String notShopPath = "src/test/resources/not-shops";

        Set<Shop> result = shopsDiscoverer.discover(notShopPath);

        assertTrue(result.isEmpty());
    }

    @Test
    void CA5_DiscoveryOnFolderWithOneShopFile_ShouldReturn_SetWithONEShop() throws FileNotFoundException {
        String simpleShopPath = "src/test/resources/simple-shop";

        Set<Shop> shops = shopsDiscoverer.discover(simpleShopPath);

        assertEquals("F", shops.iterator().next().name);
    }

    @Test
    void CA6_DiscoveryOnFolderWithTwoShopFiles_ShouldReturn_SetWithTWOShops() throws FileNotFoundException {
        String multipleShopPath = "src/test/resources/multiple-shops";

        Set<Shop> shops = shopsDiscoverer.discover(multipleShopPath);

        Iterator<Shop> iterator = shops.iterator();
        while (iterator.hasNext()) {
            Shop firstShop = iterator.next();
            assertTrue("F".equals(firstShop.name) || "G".equals(firstShop.name));
        }
    }

}