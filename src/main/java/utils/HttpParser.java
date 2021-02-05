package utils;

import webserver.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpParser {
    private static final String ROOT_PATH = "/";
    private static final String INDEX_PATH = "/index.html";

    private HttpParser() {
    }

    public static String[] parseRequestLine(String line) {
        return line.split(" ");
    }

    public static HttpMethod parseHttpMethod(String[] requestLine) {
        return HttpMethod.valueOf(requestLine[0]);
    }

    public static String parsePath(String[] requestLine) {
        String path = requestLine[1];
        int idx = path.indexOf('?');
        if (idx != -1) {
            path = path.substring(0, idx);
        }
        return path.equals(ROOT_PATH) ? INDEX_PATH : path;
    }

    public static Map<String, String> parseRequestHeader(List<String> lines) {
        Map<String, String> header = new HashMap<>();
        for (String line : lines) {
            String[] data = line.split(": ");
            header.put(data[0], data[1]);
        }
        return header;
    }

    public static int parseContentLength(Map<String, String> requestHeader) {
        return Integer.parseInt(requestHeader.getOrDefault("Content-Length", "0"));
    }

    public static Map<String, String> parseParametersFromRequestLine(String[] requestLine) {
        Map<String, String> parameters = new HashMap<>();
        String path = requestLine[1];
        int beginIdx = path.indexOf('?');
        if (beginIdx != -1) {
            String[] queries = path.substring(beginIdx + 1).split("&");
            parameters = parseParametersFromQueries(queries);
        }
        return parameters;
    }

    private static Map<String, String> parseParametersFromQueries(String[] queries) {
        Map<String, String> parameters = new HashMap<>();
        for (String query : queries) {
            String[] data = query.split("=");
            parameters.put(data[0], data[1]);
        }
        return parameters;
    }

    public static Map<String, String> parseParametersFromRequestBody(String requestBody) {
        if (requestBody == null || requestBody.length() == 0) {
            return new HashMap<>();
        }
        String[] queries = requestBody.split("&");
        return parseParametersFromQueries(queries);
    }
}
