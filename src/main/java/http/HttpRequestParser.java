package http;

import annotation.web.RequestMethod;

public class HttpRequestParser {
    private String requestLine;
    private String headerLines;
    private String body;

    public void parse(String request) {
        String[] parsedRequest = request.split("\\n\\n", 2);
        String[] parsedHead = parsedRequest[0].split("\n", 2);
        requestLine = parsedHead[0];
        headerLines = parsedHead[1];

        if (parsedRequest.length == 2) {
            body = parsedRequest[1];
        }
    }

    public RequestMethod getRequestMethod() {
        return RequestMethod.valueOf(requestLine.split(" ")[0].replace(":", ""));
    }

    public String getUri() {
        return requestLine.split(" ")[1];
    }

    public HttpRequestHeaders getRequestHeaders() {
        return HttpRequestHeaders.of(headerLines);
    }

    public String getBody() {
        return body;
    }
}
