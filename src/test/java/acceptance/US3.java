package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Article;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US3 {

    private Shoppinator shoppinator;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory();
        shoppinator = shoppinatorFactory.create("src/test/resources/US3/fravega-shop/");
    }

    @Test
    void CA1_searchExistingProductInFravegaShop() {
        Article expected = new Article("Mouse Gamer Boca Gamepro Rgb 6200dpi Usb 7 Botones", "fravega",
            new BigDecimal(5349.0));

        List<Article> retrieved = shoppinator.search("mouse");

        assertTrue(retrieved.contains(expected));
    }

    @Test
    void CA2_searchNonExistingProductInRealShop() {
        List<Article> retrievedProducts = shoppinator.search("asd");
        assertTrue(retrievedProducts.isEmpty());
    }

}