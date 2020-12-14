package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpRequestLine line;
    private final HttpRequestHeader header;
    private final ParameterBag params;

    public HttpRequest(HttpRequestLine line, HttpRequestHeader header, ParameterBag params) {
        this.line = line;
        this.header = header;
        this.params = params;
    }

    public boolean isMethod(HttpMethod method) {
        return line.getMethod() == method;
    }

    public String getPath() {
        return line.getPath();
    }

    public String getParam(String key) {

        if((line.getMethod() == HttpMethod.GET) && line.hasQueryString()) {
            return line.getQueryString().getParameter(key);
        }

        return params.getParameter(key);

    }
}
