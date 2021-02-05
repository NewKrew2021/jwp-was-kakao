package http;

import annotation.web.RequestMethod;

public class HttpRequest {
    private RequestMethod requestMethod;
    private String uri;
    private Params params;
    private HttpRequestHeaders httpRequestHeaders;
    private Body body;

    public HttpRequest(RequestMethod requestMethod, String uri) {
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public HttpRequest(RequestMethod requestMethod, String uri, Params params, HttpRequestHeaders httpRequestHeaders, Body body) {
        this(requestMethod, uri);
        this.params = params;
        this.httpRequestHeaders = httpRequestHeaders;
        this.body = body;
    }

    public String getUri() {
        return uri;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public Body getBody() {
        return body;
    }

    public Cookies getCookies() {
        return new Cookies(httpRequestHeaders.getHeader("Cookie"));
    }

    public boolean hasSameMethod(HttpRequest request) {
        return requestMethod.equals(request.requestMethod);
    }

    public boolean hasSameUri(HttpRequest request) {
        return uri.equals(request.uri);
    }

    public boolean startsWith(String compare) {
        return uri.startsWith(compare);
    }

    public boolean endsWith(String compare) {
        return uri.endsWith(compare);
    }
}