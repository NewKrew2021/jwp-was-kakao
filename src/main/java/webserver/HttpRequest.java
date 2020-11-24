package webserver;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import model.User;

import java.util.*;

import static java.util.AbstractMap.*;
import static java.util.stream.Collectors.*;

class HttpRequest {
    private final String method;
    private final String requestURI;
    private final String protocol;
    private Map<String, String> queryParams;
    private final Map<String, String> headers = new HashMap<>();
    private Map<String, String> entity;

    public HttpRequest(String method, String requestURI, String protocol) {
        this.method = method;
        this.requestURI = requestURI;
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getProtocol() {
        return protocol;
    }

    public User getUser() {
        Map<String, String> queryParams = getQueryParams();
        if (queryParams != null) {
            return User.createUser(queryParams);
        }
        Map<String, String> entity = getEntity();
        if (entity != null) {
            return User.createUser(entity);
        }
        throw new IllegalStateException();
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setEntity(Map<String, String> entity) {
        this.entity = entity;
    }

    public Map<String, String> getEntity() {
        return entity;
    }

    public Cookies getCookies() {
        return new Cookies(getHeaders().get("Cookie"));
    }

    public static class Cookies {
        public static final Splitter COOKIE_SPLITTER = Splitter.on("; ");
        public static final String KEY_VALUE_SPLIT = "=";
        private final List<String> cookies;

        public Cookies(String cookie) {
            cookies = Streams.stream(COOKIE_SPLITTER.split(cookie))
                    .map(String::trim)
                    .collect(toList());
        }

        public Map<String, String> asMap() {
            return cookies.stream()
                    .map(cookie -> {
                        String[] cookieToken = cookie.split(KEY_VALUE_SPLIT);
                        return new SimpleEntry<>(cookieToken[0], cookieToken[1]);
                    }).collect(collectingAndThen(
                            toMap(SimpleEntry::getKey, SimpleEntry::getValue),
                            ImmutableMap::copyOf));
        }

        public boolean contains(String cookie) {
            return cookies.contains(cookie);
        }
    }
}
