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
        Map<String, String> parameters = new HashMap<>();
        if (!url.contains("?")) {
            return new HttpUrl(url, parameters);
        }

        String[] urlAndParams = url.split("\\?");
        String stringParam = urlAndParams[1];
        String[] paramPairs = stringParam.split("&");
        for (String paramPair : paramPairs) {
            String[] keyAndValue = paramPair.split("=");
            parameters.put(keyAndValue[0], keyAndValue[1]);
        }

        return new HttpUrl(urlAndParams[0], parameters);
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
