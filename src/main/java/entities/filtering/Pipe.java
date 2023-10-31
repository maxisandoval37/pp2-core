package entities.filtering;

import entities.Product;
import java.io.EOFException;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Pipe {

    private final LinkedList<List<Product>> buffer;
    private boolean closed;

    public Pipe() {
        buffer = new LinkedList<List<Product>>();
        closed = false;
    }

    public void write(List<Product> s) {
        if (closed) {
            return;
        }
        buffer.add(s);
    }

    public List<Product> read() throws EOFException, InterruptedException {
        while (true) {
            if (buffer.isEmpty()) {
                if (closed) {
                    throw new EOFException();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    log.error("{} caught in Pipe", e.getMessage());
                    throw e;
                }
            } else {
                return buffer.pop();
            }
        }
    }

    /**
     * Once closed it can never be reopened
     */
    public void close() {

        closed = true;
    }

}