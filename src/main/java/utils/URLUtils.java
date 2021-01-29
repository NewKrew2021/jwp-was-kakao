package utils;

import java.util.HashMap;
import java.util.Map;

public class URLUtils {
    public static Map<String, String> parseParameter(String url) {
        Map<String, String> parameters = new HashMap<>();
        if (!url.contains("?")) {
            return parameters;
        }

        String stringParam = url.substring(url.indexOf("?") + 1);
        String[] paramPairs = stringParam.split("&");
        for (String paramPair : paramPairs) {
            String[] keyAndValue = paramPair.split("=");
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }

        return parameters;
    }
}
