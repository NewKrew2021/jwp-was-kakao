package webserver.http;

import webserver.http.utils.CookieParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequest {

    private HttpRequestLine requestLine;
    private List<HttpHeader> headers;
    private List<Cookie> cookies;
    private String body;

    public static Builder builder() {
        return new Builder();
    }

    private HttpRequest(HttpRequestLine requestLine, List<HttpHeader> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.cookies = setCookies(headers);
        this.body = body;
    }

    private List<Cookie> setCookies(List<HttpHeader> headers) {
        HttpHeader cookieHeader = headers.stream()
                .filter(it -> "Cookie".equalsIgnoreCase(it.getKey()))
                .findFirst()
                .orElse(null);

        if( cookieHeader == null ) return new ArrayList<>();

        CookieParser parser = new CookieParser();
        return parser.parse(cookieHeader.getValue());
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

    public String getCookie(String cookieName){
        return cookies.stream()
                .filter(it -> it.getName().equalsIgnoreCase(cookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
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
        private List<HttpHeader> headers = new ArrayList<>();
        private String body;

        public Builder requestLine(HttpRequestLine requestLine) {
            this.requestLine = requestLine;
            return this;
        }

        public Builder headers(List<HttpHeader> headers){
            this.headers = headers;
            return this;
        }

        public Builder headers(HttpHeader... headers){
            this.headers = Arrays.asList(headers);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            if( requestLine == null ) throw new IllegalArgumentException("request line 이 비어 있습니다");
            return new HttpRequest(requestLine, headers, body == null ? "" : body);
        }
    }
}

