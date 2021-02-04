package http;

import annotation.web.RequestMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private final String[] parsedRequest;
    private final String[] parsedHead;
    private final String[] parsedRequestLine;
    private final String[] parsedUri;

    public HttpRequestParser(String request) {
        parsedRequest = request.split("\\n\\n", 2);
        parsedHead = parsedRequest[0].split("\n", 2);
        parsedRequestLine = parsedHead[0].split(" ");
        parsedUri = parsedRequestLine[1].split("\\?", 2);
    }

    public HttpRequest parse() {
        return new HttpRequest(getRequestMethod(), getUri(), getParams(), getRequestHeaders(), getBody());
    }

    RequestMethod getRequestMethod() {
        return RequestMethod.valueOf(parsedRequestLine[0].replace(":", ""));
    }

    String getUri() {
        return parsedUri[0];
    }

    Map<String, String> getParams() {
        return getParamMap(parsedUri);
    }

    HttpRequestHeaders getRequestHeaders() {
        return HttpRequestHeaders.of(parsedHead[1]);
    }

    Map<String, String> getBody() {
        return getParamMap(parsedRequest);
    }

    Map<String, String> getParamMap(String[] parsedData) {
        Map<String, String> params = new HashMap<>();
        if (parsedData.length > 1) {
            Arrays.stream(parsedData[1].split("&"))
                    .map(param -> param.split("="))
                    .forEach(parsedParam -> params.put(parsedParam[0], parsedParam[1]));
        }
        return params;
    }
}
