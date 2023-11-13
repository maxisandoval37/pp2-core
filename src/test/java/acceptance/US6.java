package acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestUtils.getExpectedArticles;

import entities.Article;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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

    public void tearDown() {
        File triggerFile = new File("src/test/resources/refresh/trigger-refresh.txt");

        if (triggerFile.exists()) {
            triggerFile.delete();
        }
    }

    @Test
    void CA1_shouldReceiveProductsWhenShopSendNotifications() throws IOException {
        setUp("src/test/resources/refreshable-shop/");
        List<Article> expected = getExpectedArticles("a", "R");

        shoppinator.search("webcam");
        assertEquals(new ArrayList<>(), listener.products);

        File triggerFile = new File("src/test/resources/refresh/trigger-refresh-CA1.txt");
        triggerFile.createNewFile();

        while (listener.products.isEmpty()) {
            try {
                triggerFile.setLastModified(System.currentTimeMillis());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertEquals(expected, listener.products);
        triggerFile.delete();
    }



    @Test
    void CA2_shouldReceiveProductsWhenShopSendsNotifications() throws IOException {
        setUp("src/test/resources/non-refreshable-shop/");

        shoppinator.search("webcam");
        assertEquals(new ArrayList<>(), listener.products);

        File triggerFile = new File("src/test/resources/refresh/trigger-refresh-CA2.txt");
        triggerFile.createNewFile();

        assertEquals(new ArrayList<>(), listener.products);
        triggerFile.delete();
    }

    private void assertEquals(List<Article> expected, List<Article> products) {
        for(Article article : expected) {
            assertTrue(products.contains(article));
        }
    }

}
