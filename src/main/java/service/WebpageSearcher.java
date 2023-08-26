package service;

import static utils.PriceUtils.convertPriceStrToFloat;
import static utils.StringUtils.normalizeString;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import models.Product;
import models.ProductImage;
import models.ProductPresentation;
import models.ShopProduct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class WebpageSearcher {

    List<Product> searchProductsFromWebpage(String shopUrl, String productName) {
        List<Product> productList = new ArrayList<>();

        try {
            Document documentHtml = Jsoup.connect(shopUrl).get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            for (Element articleElement : articleElements) {
                Product product = mapElementToProduct(articleElement, shopUrl, productName);

                if (product != null) {
                    productList.add(product);
                }
            }
        } catch (IOException e) {
            log.warn("searchProductsFromWebpage: Error al obtener productos de la tienda {}", e.getMessage());
        }

        return productList;
    }

    private Product mapElementToProduct(Element articleElement, String shopUrl, String productName) {
        String name = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
        Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
        Element link = articleElement.select("a").first();
        // TODO replace by something like String productUrl = String.format(shopUrl, productName);
        String productUrl = "https://www.fravega.com" + link.attr("href");//TODO extraer base url
        String imageUrl = articleElement.select("img[src]").attr("src");

        try {
            if (normalizeString(name).contains(productName)){
                ProductImage productImage = new ProductImage(imageUrl);
                ShopProduct shopProduct = new ShopProduct(productUrl);
                ProductPresentation productPresentation = new ProductPresentation(BigDecimal.valueOf(price), shopProduct, productImage);

                return new Product(name, productPresentation);
            }
        }
        catch (Exception e){
            log.error("searchProducts: Levanto info que no corresponde a un producto valido");
        }

        return null;
    }
}
