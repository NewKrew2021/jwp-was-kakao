package webserver.request;

import utils.RequestParser;
import webserver.Cookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class RequestHeader {

    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String HOST_KEY = "Host";
    private static final String METHOD_KEY = "method";
    private static final String PATH_KEY = "path";


    private final List<String> lines;
    private Map<String, String> header;
    private Map<String, String> params;
    private Cookie cookie;

    private RequestHeader(List<String> lines, Map<String, String> header, Map<String, String> params, Cookie cookie) {
        this.lines = lines;
        this.header = header;
        this.params = params;
        this.cookie = cookie;
    }

    public static RequestHeader of(List<String> lines) {
        return new RequestHeader(lines, getHeader(lines), getParams(lines.get(0)), getCookie(lines));
    }

    private static Cookie getCookie(List<String> lines) {
        return lines.stream()
                .filter(line -> line.contains("Cookie"))
                .findFirst()
                .map(Cookie::of)
                .orElse(null);
    }

    private static Map<String, String> getParams(String line) {
        return RequestParser.getRequestParams(line);
    }

    private static Map<String, String> getHeader(List<String> lines) {
        Map<String, String> header = new HashMap<>();
        if (lines.size() < 1) {
            return header;
        }
        header.put(METHOD_KEY, RequestParser.getMethod(lines.get(0)));
        header.put(PATH_KEY, RequestParser.getRequestPath(lines.get(0)));
        for (String line : lines) {
            header = putContentLength(header, line);
            header = putHost(header, line);
        }
        return header;
    }

    private static Map<String, String> putContentLength(Map<String, String> header, String line) {
        return putHeader(header, line, CONTENT_LENGTH_KEY, RequestParser::getContentLength);
    }

    private static Map<String, String> putHeader(Map<String, String> header, String line, String key, Function<String, String> getValue) {
        if (line.startsWith(key)) {
            Map<String, String> newHeader = new HashMap<>(header);
            newHeader.put(key, getValue.apply(line));
            return newHeader;
        }
        return header;
    }

    private static Map<String, String> putHost(Map<String, String> header, String line) {
        return putHeader(header, line, HOST_KEY, RequestParser::getHost);
    }

    @Override
    public String toString() {
        return String.join("\n", lines);
    }

    public RequestPath getPath() {
        return new RequestPath(findInHeader(PATH_KEY));
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getMethod() {
        return findInHeader(METHOD_KEY);
    }

    public Integer getContentLength() {
        return Optional.ofNullable(findInHeader(CONTENT_LENGTH_KEY))
                .map(Integer::parseInt)
                .orElse(null);
    }

    private String findInHeader(String key) {
        if (header.containsKey(key)) {
            return header.get(key);
        }
        return null;
    }

    public String getHost() {
        return findInHeader(HOST_KEY);
    }

    public boolean cookieContains(Cookie cookie) {
        if (this.cookie == null || cookie == null) {
            return false;
        }
        return this.cookie.contains(cookie.getContent());
    }
}
