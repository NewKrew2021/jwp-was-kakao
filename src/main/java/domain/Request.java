package domain;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private final RequestHeaders requestHeaders;
    private final RequestLine requestLine;
    private final Map<String, String> bodies;
    private final Cookies cookies;

    public Request(List<String> lines) {
        this.requestHeaders = new RequestHeaders(lines);
        this.requestLine = new RequestLine(lines.get(0));
        this.bodies = makeBodies(lines.get(lines.size() - 1));
        this.cookies = requestHeaders.extractCookies();
    }

    private Map<String, String> makeBodies(String fullUrl) {
        Map<String, String> bodies = new HashMap<>();
        if (!"".equals(fullUrl)) {
            Arrays.asList(fullUrl.split("&"))
                    .forEach(query -> {
                        List<String> result = Arrays.asList(query.split("="));
                        bodies.put(decodeURL(result.get(0)), decodeURL(result.get(1)));
                    });
        }
        return bodies;
    }

    private String decodeURL(String target) {
        try {
            return URLDecoder.decode(target, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String getUrlPath() {
        return requestLine.getUrlPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getQuery(String key){
        return requestLine.getQueries().get(key);
    }

    public String getBody(String key){
        return bodies.get(key);
    }

    public Cookies getCookies(){
        return cookies;
    }

    @Override
    public String toString() {
        return "Request{" +
                "headers=" + requestHeaders +
                ", requestLine=" + requestLine +
                ", bodies=" + bodies +
                ", cookies=" + cookies +
                '}';
    }
}
