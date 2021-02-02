package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private final String urlPath;
    private final String method;
    private final Map<String, String> queries;
    private final Map<String, String> bodies;

    public Request(List<String> lines) {
        List<String> urlProperties = Arrays.asList(lines.get(0).split(" "));
        this.method = urlProperties.get(0);
        this.urlPath = makeUrlPath(urlProperties.get(1));
        this.queries = makeQueries(urlProperties.get(1));
        this.bodies = this.method.equals("POST") ? makeBodies(lines.get(lines.size() - 1)) : null;
    }

    private Map<String, String> makeBodies(String fullUrl) {
        Map<String, String> bodies = new HashMap<>();
        Arrays.asList(fullUrl.split("&"))
                .forEach(query -> {
                    List<String> result = Arrays.asList(query.split("="));
                    bodies.put(decodeURL(result.get(0)), decodeURL(result.get(1)));
                });
        return bodies;
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

    //TODO : 에러처리 해야합니다
    private String decodeURL(String target) {
        try {
            return URLDecoder.decode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getMethod(){
        return method;
    }

    public Map<String, String> getQueries(){
        return queries;
    }

    public Map<String, String> getBodies() {
        return bodies;
    }

    @Override
    public String toString() {
        return "Request{" +
                "urlPath='" + urlPath + '\'' +
                ", method='" + method + '\'' +
                ", queries=" + queries +
                '}';
    }
}
