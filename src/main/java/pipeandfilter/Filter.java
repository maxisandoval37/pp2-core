package pipeandfilter;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.Getter;
import shoppinator.core.factory.ProductFactory;
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
        out.write((s));
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

    public List<Product> jsonToProducts(String json) {
        ProductFactory pf = new ProductFactory();
        return pf.create(json);
    }
    public String productsToJson(List<Product> products) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper.writeValueAsString(products);
    }
}