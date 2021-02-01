package model;

import java.util.Map;

public class RequestMessage {
    private final String requestLine;
    private final Map<String, String> requestHeader;
    private final String requestBody;

    private RequestMessage(String requestLine, Map<String, String> requestHeader, String requestBody) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static RequestMessage of(String requestLine, Map<String, String> requestHeader, String requestBody) {
        return new RequestMessage(requestLine, requestHeader, requestBody);
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
