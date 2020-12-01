package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpMethod method;
    private String path;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public HttpRequest(HttpMethod method, String path, Map<String, String> paramters, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.parameters = paramters;
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
