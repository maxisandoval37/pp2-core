package entities.criteria;

import entities.Result;
import java.util.List;

public abstract class Criteria implements Searchable {

    abstract List<Result> meetCriteria(List<Result> result);
}
