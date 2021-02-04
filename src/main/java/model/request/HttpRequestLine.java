package model.request;

import exception.http.IllegalHttpRequestException;
import model.HttpMethod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestLine {
    private String method;
    private String path;
    private String protocol;
    private final Map<String, String> queryParameterMap = new HashMap<>();

    public HttpRequestLine(String line) {
        String[] startLine = line.split(" ");
        validateStartLine(startLine);
        method = startLine[0];
        setPath(startLine[1]);
        protocol = startLine[2];
    }

    private void setPath(String path) {
        String[] pathArgs = path.split("\\?", 2);
        this.path = pathArgs[0];
        if (pathArgs.length == 1) {
            return;
        }
        setQueryParameters(pathArgs[1]);
    }

    private void setQueryParameters(String pathArg) {
        String[] queryParams = pathArg.split("&");
        Arrays.stream(queryParams).forEach((param) -> {
            String[] args = param.split("=", 2);
            queryParameterMap.put(args[0].trim(), args[1].trim());
        });
    }

    private static void validateStartLine(String[] startLine) {
        if (startLine.length != 3 || !HttpMethod.valueOf(startLine[0]).name().equals(startLine[0]) || !startLine[1].startsWith("/")) {
            throw new IllegalHttpRequestException();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }
}
