package utils;

import webserver.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static HttpMethod parseMethodFromRequestLine(String requestLine) {
        String method = requestLine.split(" ")[0];
        return HttpMethod.valueOf(method);
    }

    public static String parseURLFromRequestLine(String requestLine) {
        return requestLine.split(" ")[1];
    }

    public static Map<String, String> parseUserParams(String query) {
        int beginIdx = query.indexOf("?");
        String[] params = query.substring(beginIdx + 1)
                .split("&");

        Map<String, String> userParams = new HashMap<>();
        for (String param : params) {
            String[] data = param.split("=");
            userParams.put(data[0], data[1]);
        }
        return userParams;
    }

    public static String parseContentTypeFromRequestHeader(Map<String, String> requestHeader) {
        return requestHeader.get("Accept")
                .split(";")[0]
                .split(",")[0];
    }
}
