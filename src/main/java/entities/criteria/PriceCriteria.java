package entities.criteria;

import entities.Result;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Setter;
import org.apache.commons.lang3.tuple.MutablePair;

public class PriceCriteria extends Criteria {

    private static final Long DEFAULT_MIN_PRICE = 0L;
    private static final Long DEFAULT_MAX_PRICE = Long.MAX_VALUE;

    @Setter
    private MutablePair<Long, Long> criteria;
    private Searchable next;

    public PriceCriteria(Searchable next) {
        this.criteria = new MutablePair<>();
        this.next = next;
    }

    @Override
    public List<Result> search(String params) {
        getCriteria(params);

        params = removeNumber(params, "[-+]\\\\d+");
        List<Result> result = next.search(params);

        return meetCriteria(result);
    }

    @Override
    public List<Result> meetCriteria(List<Result> result) {
        return result.stream().filter(r ->
                r.getPrice() >= criteria.getLeft() && r.getPrice() <= criteria.getRight())
                .collect(Collectors.toList());
    }

    private void getCriteria(String params) {
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
        return params.replaceFirst(regex, "");
    }

}
