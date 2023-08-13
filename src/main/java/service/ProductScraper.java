package service;

import models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import static utils.DomainUtils.*;
import static utils.PriceUtils.*;

@Slf4j
public class ProductScraper {
    public List<Product> scrapeProducts(String productName) {
        List<Product> allProductList = new ArrayList<>();

        allProductList.addAll(scrapeStoreProductsByName(productName));

        return allProductList;
    }

    private List<Product> scrapeStoreProductsByName(String productName) {//TODO considerar como extension
        //---------------------Fravega---------------------//
        List<Product> productList = new ArrayList<>();
        String storeUrl = "https://www.fravega.com/l/?promociones=&sorting=LOWEST_SALE_PRICE&page=1";//Los productos ya se encuentran ordenados por menor precio

        try {
            Document documentHtml = Jsoup.connect(storeUrl).get();
            Elements articleElements = documentHtml.select("article.sc-ef269aa1-2.FmCUT");

            //TODO Obtener total de pags e iterar por cada una

            for (Element articleElement : articleElements) {
                String name = articleElement.select("span.sc-6321a7c8-0.jKvHol").text();
                Float price = convertPriceStrToFloat(articleElement.select("div.sc-854e1b3a-0.kfAWhD span.sc-ad64037f-0.ixxpWu").text());
                Element link = articleElement.select("a").first();
                String productUrl = "https://www.fravega.com/" + link.attr("href");//TODO extraer base url
                String imageUrl = articleElement.select("img[src]").attr("src");

                try{
                    if (name.toLowerCase().contains(productName.toLowerCase())){
                        Product product = new Product(name, price, extractDomainName(storeUrl),productUrl, imageUrl);
                        productList.add(product);
                    }
                }
                catch (Exception e){
                    log.error("scrapeProducts: Levanto info que no corresponde a un producto valido");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Productos obtenidos de Fravega: "+productList.size());
        return productList;
    }

}