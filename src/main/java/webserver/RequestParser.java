package webserver;

import dto.RequestHeader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequestParser {

    public static RequestHeader parseHeader(String request) {
        String line = request.split("\n")[0];
        String[] parsed = line.split(" ");

        String method = parsed[0];
        String[] requestPath = parsed[1].split("\\?");
        String path = requestPath[0];
        if (requestPath.length == 1) {
            return new RequestHeader(method, path, null);
        }

        List<String> parameters = parseUserInfo(requestPath[1]);
        return new RequestHeader(method, path, parameters);
    }

    public static List<String> parseUserInfo(String request) {
        return Arrays.stream(request.split("&"))
                .map(parameter -> parameter.split("=")[1])
                .collect(Collectors.toList());
    }
}
