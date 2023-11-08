package entities.criteria;

import entities.Article;
import java.util.List;

public abstract class Criteria extends Searchable {

    abstract List<Article> meetCriteria(List<Article> article);
}
