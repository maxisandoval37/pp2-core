package entities.criteria;

import entities.Result;
import java.util.List;
import java.util.Observable;

public abstract class Criteria extends Searchable {

    abstract List<Result> meetCriteria(List<Result> result);

    /*public void update(Observable o, Object result) {
        setChanged();
        List<Result> filteredResults = meetCriteria((List<Result>) result);

        super.notifyObservers(filteredResults);
    }*/
}
