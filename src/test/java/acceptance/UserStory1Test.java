package acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinator.core.Shoppinator;
import shoppinator.core.factory.ProductFactory;
import shoppinator.core.model.Product;
import shoppinator.core.interfaces.Shop;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserStory1Test {

    private Shoppinator shoppinator;
    private Set<Shop> shops;

    @BeforeEach
    public void setUp() {
        // Configurar las instancias necesarias para las pruebas
        // Por ejemplo, configurar instancias de Shop que representen tiendas en línea (F y M)
        // También, configurar instancias de Product con precios (F.a y M.a)
        // Configurar el Shoppinator con las tiendas y productos simulados
        // shoppinator = new Shoppinator("ruta_del_directorio_de_scrappers");

        String path = "src/test/resources/scrapers/";
        shoppinator = new Shoppinator(path);
        shops = shoppinator.getShops();


    }

    @Test
    public void testComparePrices() {

        //Scraper newScrapper = Mockito.mock(Scraper.class);
        //String result = "test";
        //Mockito.when(newScrapper.scrap("test")).thenReturn(result);
        List<Product> products = shoppinator.search("a");
        ProductFactory productFactory = new ProductFactory();
        String json = "[{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}},{\"name\":\"a\",\"post_url\":\"https://example.com/\",\"product_presentation\":{\"price\":799.99,\"product_image_url\":\"https://example.com/\"}}]";
        List<Product> productsMock = productFactory.create(json);
        System.out.println(productsMock);

        assertFalse(products.isEmpty());

        // Verificar que la lista de productos contiene los productos simulados (F.a y M.a)
        assertEquals(productsMock, products);

        // Verificar que el precio de F.a sea menor o igual al precio de M.a
        assertTrue(products.get(0).getPrice() <= products.get(1).getPrice());

    }


    @Test
    public void testEmptySearchCA2() {
        // Realizar una búsqueda de productos sin especificar un nombre de producto
        List<Product> products = shoppinator.search("");
        Shop shopC = null;
        for (Shop shop : shops) {
            if (shop.getProducts().size() == 0) {
                shopC = shop;
            }
        }

        // Verificar que la lista de productos esté vacía
        assertTrue(products.isEmpty());

        // Verificar que la cantidad de productos sea igual a la cantidad especificada en C
        assertEquals(products.size(), shopC.getProducts().size());
    }

    @Test
    public void testProductNotFoundCA3() {
        // Realizar una búsqueda de un producto que no está en el conjunto supply
        List<Product> products = shoppinator.search("e");
        Shop shopC = null;
        for (Shop shop : shops) {
            if (shop.getProducts().size() == 0) {
                shopC = shop;
            }
        }
        // Verificar que la lista de productos esté vacía
        assertTrue(products.isEmpty());

        // Verificar que la cantidad de productos sea igual a la cantidad especificada en C
        assertEquals(products.size(), shopC.getProducts().size());
    }

    @Test
    public void testCoreInitializationCA4() {

        // Realizar una búsqueda de productos destacados después de la inicialización
        List<Product> products = shoppinator.search("featured");

        // Verificar que la lista de productos no esté vacía, lo que indica que se han agregado productos destacados
        assertTrue(!products.isEmpty());
    }

}
