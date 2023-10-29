package acceptance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entities.Result;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.factory.ShoppinatorFactory;
import shoppinator.core.Shoppinator;

class US1 {

    private Shoppinator shoppinator;

    private void setUp(String path) throws FileNotFoundException {
        ShoppinatorFactory shoppinatorFactory = new ShoppinatorFactory("a");
        shoppinator = shoppinatorFactory.create(path);
    }

    @Test
    void CA1_shouldReturnProductsOrderedByPriceOnSearch_WhenProductIsAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");
        List<Result> expectedResult = this.getExpectedResult("a");

        List<Result> actualResult = shoppinator.search("a");

        assertFalse(actualResult.isEmpty());
        assertEquals(expectedResult, actualResult);
        assertTrue(isSortedByPrice(expectedResult));
    }

    @Test
    void CA2_shouldNotFindProductsOnSearch_WhenProductIsNotAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/simple-shop/");

        List<Result> searchResult = shoppinator.search("e");

        assertTrue(searchResult.isEmpty());
    }

    @Test
    void CA3_shouldNotFindProductsOnSearch_WhenNoShopsAreLoaded() throws FileNotFoundException {
        this.setUp("src/test/resources/empty-folder/");

        List<Result> searchResult = shoppinator.search("a");
        assertTrue(searchResult.isEmpty());
    }

    @Test
    void CA4_shouldFindProductsOnSearch_WhenMultipleShopsAreLoaded_AndProductIsAvailableInShops() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");
        List<Result> expectedResult = this.getExpectedResult("a");

        List<Result> actualResult = shoppinator.search("a");

        assertFalse(actualResult.isEmpty());
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void CA5_shouldFindProductsOnShoppinatorCreation() throws FileNotFoundException {
        this.setUp("src/test/resources/multiple-shops/");
        List<Result> expectedResult = this.getExpectedResult("a");

        List<Result> featuredSearchResult = shoppinator.getSearchResult();

        assertFalse(featuredSearchResult.isEmpty());
        assertEquals(expectedResult, featuredSearchResult);
    }

    private List<Result> getExpectedResult(String productName) {
        List<Result> results = new ArrayList<>();
        String[] shopNames = {"F", "G"};
        Long initialPrice = 100L;

        for (String shopName : shopNames) {
            Result result = new Result(productName, shopName, initialPrice,"https://example.com/",
                "https://example.com/");
            results.add(result);

            initialPrice += 100L;
        }

        return results;
    }

    private boolean isSortedByPrice(List<Result> results) {
        Long[] prices = getProductPrices(results);

        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] > prices[i + 1]) {
                return false;
            }
        }

        return true;
    }

    private Long[] getProductPrices(List<Result> result) {
        Long[] prices = new Long[result.size()];

        for (int i = 0; i < result.size(); i++) {
            prices[i] = result.get(i).getPrice();
        }

        return prices;
    }
}