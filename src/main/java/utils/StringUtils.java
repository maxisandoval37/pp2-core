package utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    public static String normalizeString(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").toLowerCase();
    }

    /**
     * "+" --> Ej: fravega
     * "%20" --> Ej: musimundo
     * @param input, criteria
     * @return normalizedstring
     */
    public static String normalizeBlanks(String input, String criteria){
        return input.replace(" ", criteria);
    }
}