package pipeandfilter;

import java.io.EOFException;

public class Sink extends Filter {

    @Override
    public void run() {

            try {
                String productsJson = in.read();
                productsFiltered = jsonToProducts(productsJson);
            } catch(EOFException e) {
                throw new RuntimeException(e);
            }

    }

}