package webserver;

import dto.RequestValue;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final String REGEX_BLANK = " ";
    private static final String REGEX_COLON_BLANK = ": ";
    private static final String REGEX_QUESTION_MARK_AND_PERIOD = "[?.]";

    private static final String COOKIE_FIELD = "Cookie";

    private final String method;
    private final String url;
    private final String protocol;
    private final Map<String, String> header;
    private final Cookie cookie;

    private RequestHeader(String method, String url, String protocol, Map<String, String> header, Cookie cookie) {
        this.method = method;
        this.url = url;
        this.protocol = protocol;
        this.header = header;
        this.cookie = cookie;
    }

    public static RequestHeader of(RequestValue requestValue) {
        String httpRequst = requestValue.getFirstReqeustLine();

        String[] split = httpRequst.split(REGEX_BLANK);

        return new RequestHeader(split[0], split[1], split[2],
                                 parseHeader(requestValue),
                                 Cookie.of(getCookieLine(requestValue)));
    }

    public static Map<String, String> parseHeader(RequestValue requestValue) {
        Map<String, String> header = new HashMap<>();

        requestValue.getHeader().stream()
                .skip(1)
                .filter(value -> value.contains(REGEX_COLON_BLANK))
                .forEach(value -> {
                    String[] split = value.split(REGEX_COLON_BLANK);
                    header.put(split[0], split[1]);
                });

        return header;
    }

    public static String getCookieLine(RequestValue requestValue) {
        return requestValue.getHeader().stream()
                .filter(value -> value.contains(COOKIE_FIELD))
                .findAny()
                .orElse(null);
    }

    public String getMethod() {
        return method;
    }

    public String getURL() {
        return url;
    }

    public String getPathGateway() {
        return url.split(REGEX_QUESTION_MARK_AND_PERIOD)[0];
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public String getHeader(String key) {
        return this.header.get(key);
    }

    public boolean isLogined() {
        return cookie.isLogined();
    }
}
