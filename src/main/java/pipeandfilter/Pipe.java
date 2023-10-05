package pipeandfilter;

import java.io.EOFException;
import java.util.LinkedList;


public class Pipe {
    private final LinkedList<String> buffer;
    private boolean closed;

    public Pipe() {
        buffer = new LinkedList<String>();
        closed = false;
    }

    public void write(String s) {
        if(closed)
            return;
        buffer.add(s);
    }

    public String read() throws EOFException {
        while(true) {
            if(buffer.isEmpty()) {
                if(closed)
                    throw new EOFException();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.err.println("InterruptedExcpetion caught in Pipe");
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