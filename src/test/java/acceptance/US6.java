package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.getExpectedArticles;

import entities.Article;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;
import utils.ProductsListener;

class US6 {

    private Shoppinator shoppinator;
    private ProductsListener listener;

    public void setUp(String path) throws FileNotFoundException {
        listener = new ProductsListener();

        ShoppinatorFactory factory = new ShoppinatorFactory();
        shoppinator = factory.create(path);
        shoppinator.addObserver(listener);
    }

    @Test
    void CA1_shouldReceiveProductsWhenShopSendNotifications() throws IOException {
        setUp("src/test/resources/US6/refreshable-shop/");
        List<Article> expected = getExpectedArticles("a", "R");

        shoppinator.search("webcam");
        assertTrue(listener.products.isEmpty());

        File triggerFile = new File("src/test/resources/US6/refresh/trigger-refresh-CA1.txt");
        triggerFile.createNewFile();

        while (listener.products.isEmpty()) {
            try {
                triggerFile.setLastModified(System.currentTimeMillis());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertTrue(listener.products.equals(expected));
        triggerFile.delete();
    }

    @Test
    void CA2_shouldReceiveProductsWhenShopSendsNotifications() throws IOException {
        setUp("src/test/resources/US6/non-refreshable-shop/");

        shoppinator.search("webcam");
        assertTrue(listener.products.isEmpty());

        File triggerFile = new File("src/test/resources/US6/refresh/trigger-refresh-CA2.txt");
        triggerFile.createNewFile();

        assertTrue(listener.products.isEmpty());
        triggerFile.delete();
    }

}
