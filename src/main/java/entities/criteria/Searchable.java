package entities.criteria;

import entities.Article;
import java.util.List;
import java.util.Observable;

public abstract class Searchable extends Observable {

    public abstract List<Article> search(String query);
}
