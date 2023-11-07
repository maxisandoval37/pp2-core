package entities.criteria;

import entities.Result;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.MutablePair;

@SuppressWarnings("deprecation")
public class PriceCriteria extends Criteria implements Observer {

    private static final Long DEFAULT_MIN_PRICE = 0L;
    private static final Long DEFAULT_MAX_PRICE = Long.MAX_VALUE;

    private MutablePair<Long, Long> criteria;

    private final Searchable next;

    public PriceCriteria(Searchable next) {
        this.criteria = new MutablePair<>();
        this.next = next;
        next.addObserver(this);
    }

    @Override
    public List<Result> search(String params) {
        setCriteria(params);

        params = removeNumber(params, "[-+]\\\\d+");
        List<Result> result = next.search(params);

        return meetCriteria(result);
    }

    @Override
    public void update(Observable o, Object result) {
        setChanged();
        List<Result> filteredResults = meetCriteria((List<Result>) result);

        super.notifyObservers(filteredResults);
    }

    @Override
    public List<Result> meetCriteria(List<Result> result) {
        return result.stream().filter(r ->
                r.getPrice() >= criteria.getLeft() && r.getPrice() <= criteria.getRight())
                .sorted((r1, r2) -> (int) (r1.getPrice() - r2.getPrice()))
                .collect(Collectors.toList());
    }

    private void setCriteria(String params) {
        String priceMin = extractNumber(params, "-(\\d+)");
        String priceMax = extractNumber(params, "\\+(\\d+)");

        this.criteria.setLeft((priceMin.isEmpty())
            ? DEFAULT_MIN_PRICE : Long.valueOf(priceMin));
        this.criteria.setRight((priceMax.isEmpty())
            ? DEFAULT_MAX_PRICE : Long.valueOf(priceMax));
    }

    private String extractNumber(String params, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(params);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    private String removeNumber(String params, String regex) {
        params = params.replaceFirst("[+]\\d+", "");
        params = params.replaceFirst("[-]\\d+", "");
        return params;
    }

}
