package pipeAndFilter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import shoppinator.core.factory.ProductFactory;
import shoppinator.core.model.Product;
import lombok.Data;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;


public class PriceFilter extends Filter {

    private final String data;

    public PriceFilter(String data) {
        this.data = data;
    }


    @Override
    public void run() {

        List<Product> products = new ArrayList<>();
/*
        try {
            while (true) {
                // Leer productos como cadenas JSON de la tubería de entrada
                String productoJson = read();
                if (productoJson == null) {
                    break; // Se alcanzó el final de la tubería de entrada
                }

                // Convertir la cadena JSON en un objeto Producto


            }
        } catch (EOFException e) {
            // Se alcanzó el final de la tubería de entrada
        }
*/
        products = jsonToProduct(data);

        for (Product product : products) {
            if (product.getProductPresentation().getPrice() >= 500 && product.getProductPresentation().getPrice() <= 2000) {
                productsFiltered.add(product);
            }
        }

        // Convertir la lista de productos filtrados a cadenas JSON individuales y enviarlas
        for (Product productFiltered : productsFiltered) {
            String productFilteredJson = null;
            try {
                productFilteredJson = productToJson(productFiltered);
                write(productFilteredJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        // Cerrar la tubería de salida
        out.close();

    }

    private String productToJson(Object product) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(product);
    }

    private List<Product> jsonToProduct(String json) {
        ProductFactory pf = new ProductFactory();
        return pf.create(json);
    }

}