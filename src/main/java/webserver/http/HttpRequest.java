package webserver.http;

import java.util.List;

public class HttpRequest {

    private HttpRequestLine requestLine;
    private List<String> headers;

    public HttpRequest(List<String> requestMessageLines) {
        parseRequestLine(requestMessageLines.get(0));
        this.headers = requestMessageLines.subList(1,requestMessageLines.size());
    }

    private void parseRequestLine(String requestLineString) {
        requestLine = new HttpRequestLine(requestLineString);
    }

    public HttpRequestLine getRequestLine(){
        return requestLine;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(requestLine).append("\n");
        headers.forEach(header -> sb.append(header).append("\n"));
        return sb.toString();
    }

    public String getRequestUri() {
        return requestLine.getUri();
    }
}

