package utils;

import java.util.HashMap;
import java.util.Map;

public class KeyValueTokenizer {

    public static final String DELIM_AMPERSAND = "&";
    public static final String DELIM_EQUAL = "=";

    public static Map<String, String> of(String input) {
        Map<String, String> parameters = new HashMap<>();
        String[] tokens = input.split(DELIM_AMPERSAND);
        for (String token : tokens) {
            Pair pair = Pair.from(token);
            parameters.put(pair.key, pair.value);
        }
        return parameters;
    }

    private static class Pair {
        private final String key;
        private final String value;

        private Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public static Pair from(String rawString) {
            String[] split = rawString.split(DELIM_EQUAL);
            String key = split[0];
            String value = "";
            if (split.length > 1) value = split[1];
            return new Pair(key, value);
        }
    }
}
