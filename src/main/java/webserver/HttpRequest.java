package webserver;

import model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
            return new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"), queryParams.get("email"));
        }
        Map<String, String> entity = getEntity();
        if (entity != null) {
            return new User(entity.get("userId"), entity.get("password"), entity.get("name"), entity.get("email"));
        }
        return null;
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
        public Cookies(String cookie) {
        }

        public Map<String, String> asMap() {
            Map<String, String> map = new HashMap<>();
            map.put("logined", "true");
            map.put("Idea-32c00508", "32c00508=37ab5797-f595-40c6-b63f-d4e27524f593");
            map.put("Idea-32c008c9", "b5b2b305-3b96-4335-9659-dfa0d33877fd");
            return Collections.unmodifiableMap(map);
        }
    }
}
