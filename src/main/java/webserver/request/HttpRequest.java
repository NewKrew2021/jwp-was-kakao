package webserver.request;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HttpRequest {

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private RequestHeader header;
    private Map<String, String> bodyParams;

    HttpRequest() {
        header = RequestHeader.empty();
        protocol = Protocol.HTTP;
        bodyParams = new HashMap<>();
    }

    public static HttpRequest of(List<String> lines) {
        return RequestBuilder.fromLines(lines);
    }

    public static HttpRequest empty() {
        return new HttpRequest();
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public void setBodyParams(String body) {
        this.bodyParams = RequestParamParser.parseRequestParams(body);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public String getParameter(String key) {
        return Optional.ofNullable(header.getParameter(key))
                .orElse(getParameterFromBody(key));
    }

    private String getParameterFromBody(String key) {
        if (bodyParams.containsKey(key)) {
            return bodyParams.get(key);
        }
        return null;
    }

    public RequestHeader getHeader() {
        return header;
    }
}
