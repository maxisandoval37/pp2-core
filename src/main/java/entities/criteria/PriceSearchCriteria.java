package entities.criteria;

import entities.Article;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;

@SuppressWarnings("deprecation")
public class PriceSearchCriteria extends Criteria implements Observer {

    private static final BigDecimal DEFAULT_MIN_PRICE = BigDecimal.ZERO;
    private static final BigDecimal DEFAULT_MAX_PRICE = BigDecimal.valueOf(Long.MAX_VALUE);

    private MutablePair<BigDecimal, BigDecimal> criteria;

    private final Searchable next;

    public PriceSearchCriteria(Searchable next) {
        this.criteria = new MutablePair<>();
        this.next = next;
        next.addObserver(this);
    }

    @Override
    public List<Article> search(String query) {
        setCriteria(query);

        query = removeNumber(query, "[-+]\\\\d+");
        List<Article> article = next.search(query);

        return meetCriteria(article);
    }

    @Override
    public void update(Observable o, Object result) {
        setChanged();
        List<Article> filteredArticles = meetCriteria((List<Article>) result);

        super.notifyObservers(filteredArticles);
    }

    @Override
    public List<Article> meetCriteria(List<Article> articles) {
        return articles.stream()
            .filter(article ->
                article.getPrice().compareTo(criteria.getLeft()) >= 0 &&
                    article.getPrice().compareTo(criteria.getRight()) <= 0)
            .sorted(Comparator.comparing(Article::getPrice))
            .collect(Collectors.toList());
    }


    private void setCriteria(String query) {
        String priceMin = extractNumber(query, "-(\\d+)");
        String priceMax = extractNumber(query, "\\+(\\d+)");

        this.criteria.setLeft((priceMin.isEmpty())
            ? DEFAULT_MIN_PRICE : new BigDecimal(priceMin));
        this.criteria.setRight((priceMax.isEmpty())
            ? DEFAULT_MAX_PRICE : new BigDecimal(priceMax));
    }

    private String extractNumber(String params, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(params);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    private String removeNumber(String query, String regex) {
        query = query.replaceFirst("[+]\\d+", "");
        query = query.replaceFirst("-\\d+", "");
        return query;
    }

}
