package http;

import annotation.web.RequestMethod;
import utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private RequestMethod requestMethod;
    private String uri;
    private Map<String, String> params = new HashMap<>();
    private HttpRequestHeaders httpRequestHeaders;
    private String body;

    public HttpRequest(RequestMethod requestMethod, String uri) {
        this.requestMethod = requestMethod;
        parseUri(uri);
    }

    public HttpRequest(RequestMethod requestMethod, String uri, HttpRequestHeaders httpRequestHeaders, String body) {
        this(requestMethod, uri);
        this.httpRequestHeaders = httpRequestHeaders;
        this.body = body;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public HttpRequestHeaders getRequestHeaders() {
        return httpRequestHeaders;
    }

    public String getBody() {
        return body;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    private void parseUri(String uri) {
        String[] parsedUri = uri.split("\\?", 2);
        this.uri = parsedUri[0];
        if(parsedUri.length < 2) {
            return;
        }
        this.params = HttpUtils.getParamMap(parsedUri[1]);
    }

    public boolean sameRequestLine(HttpRequest httpRequest) {
        return requestMethod.equals(httpRequest.requestMethod) && uri.equals(httpRequest.uri);
    }

    public boolean isTemplateRequest() {
        return requestMethod == RequestMethod.GET && uri.endsWith(".html");
    }
}