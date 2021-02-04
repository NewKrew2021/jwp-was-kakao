package web;

import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    private static final String PARAM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String START_PARAMETER = "?";
    private static final int SPLIT_SIZE = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int URL = 0;
    private static final int PARAMS = 1;

    private final String url;
    private final Map<String, String> parameters;

    private HttpUrl(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    public static HttpUrl of(String url) {
        if (!url.contains(START_PARAMETER)) {
            return new HttpUrl(url, new HashMap<>());
        }

        String[] urlAndParams = url.split("\\" + START_PARAMETER, SPLIT_SIZE);
        return new HttpUrl(urlAndParams[URL], parseParameter(urlAndParams[PARAMS]));
    }

    public static Map<String, String> parseParameter(String stringParam) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramPairs = stringParam.split(PARAM_DELIMITER);
        for (String paramPair : paramPairs) {
            String[] keyAndValue = paramPair.split(KEY_VALUE_DELIMITER);
            parameters.put(keyAndValue[KEY], keyAndValue[VALUE]);
        }
        return parameters;
    }

    public boolean hasSameUrl(String url) {
        return this.url.equals(url);
    }

    public boolean endsWith(String url) {
        return this.url.endsWith(url);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getUrl() {
        return url;
    }
}
