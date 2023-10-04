package pipeAndFilter;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import pipeAndFilter.Pipe;
import shoppinator.core.model.Product;

@Getter
public abstract class Filter implements Runnable {

    List<Product> productsFiltered = new ArrayList<>();
    protected Pipe in, out;

    public void setIn(Pipe p) {
        in = p;
    }

    public void setOut(Pipe p) {
        out = p;
    }

    /**
     * Writes to it's pipe
     *
     * @param s
     */
    public void write(String s) {
        if (s == null) {
            out.close();
            return;
        }
        out.write(s);
    }

    /**
     * Reads from it's pipe
     *
     * @return
     * @throws EOFException
     */
    public String read() throws EOFException {
        return in.read();
    }

}