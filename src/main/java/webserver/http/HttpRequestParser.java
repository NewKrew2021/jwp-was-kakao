package webserver.http;

import annotation.web.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private String requestLine;
    private String headerLines;
    private String body;

    public static HttpRequest getRequest(String request) {
        String[] parsedRequest = request.split("\\n\\n", 2);
        String[] parsedHead = parsedRequest[0].split("\n", 2);
        String requestLine = parsedHead[0];
        String headerLines = parsedHead[1];

        return new HttpRequest(
                getRequestMethod(requestLine),
                getUri(requestLine),
                getParams(requestLine),
                getRequestHeaders(headerLines),
                parsedRequest.length == 2 ? parsedRequest[1] : null
        );
    }

    private static RequestMethod getRequestMethod(String requestLine) {
        return RequestMethod.valueOf(requestLine.split(" ")[0].replace(":", ""));
    }

    private static String getUri(String requestLine) {
        return requestLine.split(" ")[1].split("\\?", 2)[0];
    }

    private static Map<String, String> getParams(String requestLine) {
        Map<String, String> params = new HashMap<>();
        String []parsedUri = requestLine.split(" ")[1].split("\\?", 2);
        if(parsedUri.length == 1) {
            return params;
        }
        for(String param : parsedUri[1].split("&")) {
            params.put(param.split("=")[0], param.split("=")[1]);
        }
        return params;
    }

    private static HttpRequestHeaders getRequestHeaders(String headerLines) {
        return HttpRequestHeaders.of(headerLines);
    }

}
