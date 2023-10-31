package entities.filtering;

import java.io.EOFException;

public class Sink extends Filter {

    @Override
    public void run() {
        try {
            filteredProducts = in.read();
        } catch (EOFException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}