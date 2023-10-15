package entities.filtering;

import java.io.EOFException;

public class Sink extends Filter {

    @Override
    public void run() {
        try {
            String productsJson = in.read();
            filteredProducts = jsonToProducts(productsJson);
        } catch (EOFException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}