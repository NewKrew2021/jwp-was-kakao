package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private final String urlPath;
    private final String method;
    private final RequestHeaders headers;
    private final Map<String, String> queries;
    private final Map<String, String> bodies;
    private final Cookies cookies;

    public Request(List<String> lines) {
        List<String> urlProperties = Arrays.asList(lines.get(0).split(" "));
        this.headers = new RequestHeaders(lines);
        this.method = urlProperties.get(0);
        this.urlPath = makeUrlPath(urlProperties.get(1));
        this.queries = makeQueries(urlProperties.get(1));
        this.bodies = makeBodies(lines.get(lines.size() - 1));
        this.cookies = headers.extractCookies();
    }

    private Map<String, String> makeBodies(String fullUrl) {
        Map<String, String> bodies = new HashMap<>();
        if (!fullUrl.equals("")) {
            Arrays.asList(fullUrl.split("&"))
                    .forEach(query -> {
                        List<String> result = Arrays.asList(query.split("="));
                        bodies.put(decodeURL(result.get(0)), decodeURL(result.get(1)));
                    });
        }
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

    public String getMethod() {
        return method;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public Map<String, String> getBodies() {
        return bodies;
    }

    @Override
    public String toString() {
        return "Request{" +
                "urlPath='" + urlPath + "'\n" +
                ", method='" + method + "'\n" +
                ", headers=" + headers + "\n" +
                ", queries=" + queries + "\n" +
                ", bodies=" + bodies + "\n" +
                ", cookies=" + cookies +
                '}';
    }

    public Cookies getCookies(){
        return cookies;
    }
}
