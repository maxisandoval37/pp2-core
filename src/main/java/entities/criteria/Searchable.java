package entities.criteria;

import entities.Result;
import java.util.List;
import java.util.Observable;

public abstract class Searchable extends Observable {

    public abstract List<Result> search(String params);
}
