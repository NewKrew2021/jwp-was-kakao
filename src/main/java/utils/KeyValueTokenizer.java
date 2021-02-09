package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class KeyValueTokenizer {

    public static final String DELIM = "=&";

    public static Map<String, String> of(String input) {
        Map<String, String> parameters = new HashMap<>();
        StringTokenizer stk = new StringTokenizer(input, DELIM);
        while (stk.hasMoreTokens()) {
            parameters.put(stk.nextToken(), stk.nextToken());
        }
        return parameters;
    }

}
