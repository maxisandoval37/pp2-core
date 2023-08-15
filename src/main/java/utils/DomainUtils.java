package utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DomainUtils {
    public static String extractDomainName(String urlString) {
        try {
            URI uri = new URI(urlString);
            String host = uri.getHost();

            Pattern pattern = Pattern.compile("(?:www\\.)?(.*)\\..*");
            Matcher matcher = pattern.matcher(host);

            if (matcher.matches()) {
                return matcher.group(1);
            }
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}