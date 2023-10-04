package pipeAndFilter;

import java.io.EOFException;

public class Sink extends Filter {

    @Override
    public void run() {
        while(true) {
            try {
                System.out.print(read());
            } catch(EOFException e) {
                break;
            }
        }
    }

}