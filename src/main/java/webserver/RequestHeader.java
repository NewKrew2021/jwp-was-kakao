package webserver;

import dto.RequestValue;

public class RequestHeader {

    private static final String REGEX_BLANK = " ";
    private static final String REGEX_QUESTION_MARK_AND_PERIOD = "[?.]";

    private static final String COOKIE_FIELD = "Cookie";

    private final String method;
    private final String url;
    private final Cookie cookie;

    private RequestHeader(String method, String url, Cookie cookie) {
        this.method = method;
        this.url = url;
        this.cookie = cookie;
    }

    public static RequestHeader of(RequestValue requestValue) {
        String httpRequst = requestValue.getFirstReqeustLine();

        String[] split = httpRequst.split(REGEX_BLANK);

        return new RequestHeader(split[0],
                                 split[1],
                                 Cookie.of(getCookieLine(requestValue)));
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


    public boolean isLogined() {
        return cookie.isLogined();
    }
}
