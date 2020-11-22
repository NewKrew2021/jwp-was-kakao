package webserver;

import model.User;

import java.util.HashMap;
import java.util.Map;

class HttpRequest {
    private final String method;
    private final String requestURI;
    private final String protocol;
    private Map<String, String> queryParams;

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
        return new User(queryParams.get("userId"), queryParams.get("password"), queryParams.get("name"), queryParams.get("email"));
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Length", "59");
        headers.put("Content-type", "application/x-www-form-urlencoded");
        headers.put("Accept", "*/*");
        return headers;
    }
}
