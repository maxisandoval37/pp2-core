package entities.filtering;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import entities.Product;

@Getter
public abstract class Filter implements Runnable {

    List<Product> filteredProducts = new ArrayList<>();
    protected Pipe in;
    protected Pipe out;

    public void setIn(Pipe p) {
        in = p;
    }

    public void setOut(Pipe p) {
        out = p;
    }

    public void write(List<Product> s) {
        if (s == null) {
            out.close();
            return;
        }
        out.write((s));
    }

    public List<Product> read() throws EOFException, InterruptedException {
        return in.read();
    }

}