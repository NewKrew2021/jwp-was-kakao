package webserver.http;

public class HttpRequestLine {

    private final HttpMethod method;
    private final String path;
    private final ParameterBag queryString;
    private final Protocol protocol;

    public HttpRequestLine(HttpMethod method, String path, ParameterBag queryString, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.queryString = queryString;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public boolean hasQueryString() {
        return queryString!= null;
    }

    public ParameterBag getQueryString() {
        return queryString;
    }

    public String getProtocol() {
        return protocol.getProtocol();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

}
