package utils;

import webserver.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    
    public static Map<String, String> parseHeaderFromRequestMessage(List<String> requestMessage) {
        Map<String, String> header = new HashMap<>();
        for(String line : requestMessage) {
            if (line.equals("")) { // TO DO: indent 줄이기
                break;
            }
            String[] data = line.split(": ");
            header.put(data[0], data[1]);
        }
        return header;
    }

    public static HttpMethod parseMethodFromRequestLine(String requestLine) {
        String method = requestLine.split(" ")[0];
        return HttpMethod.valueOf(method);
    }

    public static String parseURLFromRequestLine(String requestLine) {
        return requestLine.split(" ")[1];
    }

    public static String parseBodyFromRequestMessage(List<String> requestMessage) {


        return null;
    }

    public static Map<String, String> parseUserParams(String query) {
        int beginIdx = query.indexOf("?");
        String[] params = query.substring(beginIdx + 1)
                .split("&");

        Map<String, String> userParams = new HashMap<>();
        for(String param : params) {
            String[] data = param.split("=");
            userParams.put(data[0], data[1]);
        }
        return userParams;
    }
}
