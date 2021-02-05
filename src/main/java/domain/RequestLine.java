package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestLine {
    private final String method;
    private final String urlPath;
    private final Map<String, String> queries;

    public RequestLine(String requestLine){
        List<String> urlProperties = Arrays.asList(requestLine.split(" "));
        this.method = urlProperties.get(0);
        this.urlPath = makeUrlPath(urlProperties.get(1));
        this.queries = makeQueries(urlProperties.get(1));
    }

    private String decodeURL(String target) {
        try {
            return URLDecoder.decode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String makeUrlPath(String fullUrl) {
        return fullUrl.split("\\?")[0];
    }

    private Map<String, String> makeQueries(String fullUrl) {
        Map<String, String> queries = new HashMap<>();
        if (fullUrl.contains("?")) {
            Arrays.asList(fullUrl.split("\\?")[1]
                    .split("&"))
                    .forEach(query -> {
                        List<String> result = Arrays.asList(query.split("="));
                        queries.put(decodeURL(result.get(0)), decodeURL(result.get(1)));
                    });
        }
        return queries;
    }

    public String getMethod() {
        return method;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    @Override
    public String toString() {
        return "RequestLine{" +
                "method='" + method + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", queries=" + queries +
                '}';
    }
}
