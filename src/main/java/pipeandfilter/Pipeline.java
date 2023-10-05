package pipeandfilter;

public class Pipeline implements Runnable {

    private final Filter[] filters;

    public Pipeline(Filter ... filters ) {
        this.filters = filters;

        for(int i = 0; i < filters.length - 1; i++) {
            Pipe p = new Pipe();
            filters[i].setOut(p);
            filters[i+1].setIn(p);
        }
    }

    @Override
    public void run() {
        for(Filter f : filters) {
            (new Thread(f)).run();

        }
    }
}