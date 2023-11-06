package entities.criteria;

import entities.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class ShopsSelectionCriteria extends Criteria implements Observer {

    private List<String> criteria;
    private final Searchable next;

    public ShopsSelectionCriteria(Searchable next) {
        this.criteria = new ArrayList<>();
        this.next = next;
        next.addObserver(this);
    }

    @Override
    public List<Result> search(String params) {
        setCriteria(params);

        params = removeHashTags(params);
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
        if (criteria == null) {
            return result;
        }

        List<Result> filteredResult = new ArrayList<>();
        for (String shopName : criteria) {
            filteredResult.addAll(result.stream()
                .filter(r -> r.getShopName().equals(shopName))
                .collect(Collectors.toList()));
        }
        return filteredResult;
    }

    private void setCriteria(String params) {
        this.criteria = extractHashTags(params);
    }

    private List<String> extractHashTags(String params) {
        List<String> hashTags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(params);
        while (matcher.find()) {
            hashTags.add(matcher.group(1));
        }
        return hashTags;
    }

    private String removeHashTags(String params) {
        return params.replaceAll("#\\w+", "");
    }

}
