package entities.criteria;

import entities.Result;
import java.util.List;

public interface Searchable {

    List<Result> search(String params);
}
