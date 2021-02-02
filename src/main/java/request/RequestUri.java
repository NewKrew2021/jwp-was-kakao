package request;

import annotation.web.RequestMethod;

import java.util.Map;

public class RequestUri {
    private RequestMethod requestMethod;
    private String uri;
    private Map<String, String> params;

    public RequestUri(RequestMethod requestMethod, String uri, Map<String, String> params) {
        this.requestMethod = requestMethod;
        this.uri = uri;
        this.params = params;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
