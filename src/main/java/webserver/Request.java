package webserver;

import dto.ParamValue;
import dto.RequestValue;

import java.util.Optional;

public class Request {

    private static final String REGEX_QUESTION_MARK_AND_PERIOD = "[?.]";

    private final String url;
    private final String method;
    private final Optional<ParamValue> paramMap;

    private final Cookie cookie;

    private Request(String url, String method, Optional<ParamValue> paramMap, Cookie cookie) {
        this.url = url;
        this.method = method;
        this.paramMap = paramMap;
        this.cookie = cookie;
    }

    public static Request of(RequestValue requestValue) {
        String url = requestValue.getURL();
        String method = requestValue.getMethod();
        Optional<ParamValue> param = ParamValue.of(requestValue.getParams());

        Cookie cookie = Cookie.of(requestValue.getCookieLine());

        return new Request(url, method, param, cookie);
    }

    public String getURL() {
        return url;
    }

    public String getPathGateway() {
        return url.split(REGEX_QUESTION_MARK_AND_PERIOD)[0];
    }


    public Optional<ParamValue> getParamMap() {
        return paramMap;
    }

    public boolean isLogined() {
        return cookie.isLogined();
    }
}
