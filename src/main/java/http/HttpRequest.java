package http;

import annotation.web.RequestMethod;

import java.util.Map;

public class HttpRequest {
    private RequestMethod requestMethod;
    private String uri;
    private Map<String, String> params;
    private HttpRequestHeaders httpRequestHeaders;
    private Map<String, String> body;

    public HttpRequest(RequestMethod requestMethod, String uri) {
        this.requestMethod = requestMethod;
        this.uri = uri;
    }

    public HttpRequest(RequestMethod requestMethod, String uri, Map<String, String> params, HttpRequestHeaders httpRequestHeaders, Map<String, String> body) {
        this(requestMethod, uri);
        this.params = params;
        this.httpRequestHeaders = httpRequestHeaders;
        this.body = body;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public Cookies getCookies() {
        return new Cookies(httpRequestHeaders.getHeader("Cookie"));
    }

    public boolean matchWith(HttpRequest request) {
        if (isTemplateRequest() && request.isTemplateRequest()) {
            return true;
        }
        if (isStaticRequest() && request.isStaticRequest()) {
            return true;
        }
        return sameRequestLine(request);
    }

    public boolean sameRequestLine(HttpRequest request) {
        return requestMethod.equals(request.requestMethod) && uri.equals(request.uri);
    }

    public boolean isTemplateRequest() {
        return requestMethod == RequestMethod.GET && uri.endsWith(".html");
    }

    public boolean isStaticRequest() {
        return requestMethod
                == RequestMethod.GET && (uri.startsWith("/css") || uri.startsWith("/fonts") || uri.startsWith("/images") || uri.startsWith("/js"));
    }
}