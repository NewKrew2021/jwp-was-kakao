package webserver;

import utils.ParamMap;
import utils.Utils;
import webserver.constant.HttpHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Cookie {

    private ParamMap values = new ParamMap();

    // TODO attrs should be applied to Set-Cookie respectively
    private List<String> attributes = new ArrayList<>(); // for res (Set-Cookie) only

    public Cookie() {
    }

    public Cookie(String cookieHeaderValue) {
        if (Utils.isNotEmtpy(cookieHeaderValue)) {
            values = new ParamMap(cookieHeaderValue, "; ", "=", Function.identity());
        }
    }

    public Cookie putValue(String k, String v) {
        values.put(k, v);
        return this;
    }

    public Cookie putAttribute(String attr) {
        attributes.add(attr);
        return this;
    }

    public String getValueOrDefault(String k, String defaultValue) {
        return values.getOrDefault(k, defaultValue);
    }

    public HttpHeaders buildSetCookieHeaders() {
        String attrs = attributes.stream().collect(Collectors.joining("; "));
        if (attrs.length() > 0) {
            attrs = "; " + attrs;
        }
        String finalAttrs = attrs;

        HttpHeaders setCookieHeaders = new HttpHeaders();

        // FIXME bad
        Arrays.stream(values.join(";", "=").split(";"))
                .forEach(cookieNameValue -> {
                    setCookieHeaders.addHeader(HttpHeader.SET_COOKIE, cookieNameValue + finalAttrs);
                });

        return setCookieHeaders;
    }

}
