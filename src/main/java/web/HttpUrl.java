package web;

import java.util.HashMap;
import java.util.Map;

public class HttpUrl {
    private final String url;
    private final Map<String, String> parameters;

    private HttpUrl(String url, Map<String, String> parameters) {
        this.url = url;
        this.parameters = parameters;
    }

    public static HttpUrl of(String url) {
        if (!url.contains("?")) {
            return new HttpUrl(url, new HashMap<>());
        }

        String[] urlAndParams = url.split("\\?", 2);
        return new HttpUrl(urlAndParams[0], parseParameter(urlAndParams[1]));
    }

    public static Map<String, String> parseParameter(String stringParam) {
        Map<String, String> parameters = new HashMap<>();
        String[] paramPairs = stringParam.split("&");
        for (String paramPair : paramPairs) {
            String[] keyAndValue = paramPair.split("=");
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }
        return parameters;
    }

    public boolean hasSameUrl(String url) {
        return this.url.equals(url);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public String getUrl() {
        return url;
    }
}
