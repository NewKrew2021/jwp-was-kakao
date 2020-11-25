package webserver.http;

import java.util.List;

public class HttpRequest {

    private HttpRequestLine requestLine;
    private List<HttpHeader> headers;
    private String body;

    public static Builder builder() {
        return new Builder();
    }

    private HttpRequest(HttpRequestLine requestLine, List<HttpHeader> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public List<HttpRequestParam> getParams() {
        return requestLine.getParams();
    }

    public HttpRequestParam getParam(String name){
        return requestLine.getParams().stream()
                .filter(it -> it.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(requestLine).append("\n");
        headers.forEach(header -> sb.append(header).append("\n"));
        return sb.toString();
    }

    public String getPath() {
        return requestLine.getUri().getPath();
    }

    public String getRequestLine(){
        return requestLine.toString();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }

    static class Builder {

        private HttpRequestLine requestLine;
        private List<HttpHeader> headers;
        private String body;

        public Builder requestLine(HttpRequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public Builder headers(List<HttpHeader> headers){
            this.headers = headers;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(requestLine, headers, body == null ? "" : body);
        }
    }
}

