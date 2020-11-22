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
}
