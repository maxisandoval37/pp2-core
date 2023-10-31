package entities.filtering;

import entities.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class Pump extends Filter {

    private List<Product> data;

    @Override
    public void run() {

        out.write(data);
    }

}