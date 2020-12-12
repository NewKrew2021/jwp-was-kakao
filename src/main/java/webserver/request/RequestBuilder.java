package webserver.request;

import org.springframework.http.HttpMethod;
import webserver.Cookie;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.request.RequestHeader.COOKIE;

public class RequestBuilder {
    public static HttpRequest fromLines(List<String> lines) {
        HttpRequest request = new HttpRequest();
        applyFirstLine(request, lines.get(0));
        applyRestLines(request, lines.subList(1, lines.size()));
        return request;
    }

    private static void applyFirstLine(HttpRequest request, String line) {
        String[] tokens = line.split(" ");
        assert tokens.length == 3;
        request.setMethod(HttpMethod.valueOf(tokens[0].toUpperCase()));
        setPath(request, tokens[1]);
        request.setProtocol(Protocol.fromMessage(tokens[2].toUpperCase()));
    }

    private static void setPath(HttpRequest request, String token) {
        String[] pathTokens = token.split("\\?");
        request.setPath(pathTokens[0]);
        if (pathTokens.length == 2) {
            request.getHeader().setParams(RequestParamParser.parseRequestParams(pathTokens[1]));
        }
    }

    private static void applyRestLines(HttpRequest request, List<String> lines) {
        for (String line : lines) {
            String key = line.substring(0, line.indexOf(":")).trim();
            String value = line.substring(line.indexOf(":") + 1).trim();
            request.getHeader().addHeader(key, getValue(key, value));
        }
    }

    private static Object getValue(String key, String value) {
        if (isCookie(key)) {
            return Arrays.stream(value.split(";"))
                    .map(Cookie::fromRequest)
                    .collect(Collectors.toList());
        }
        return value;
    }

    private static boolean isCookie(String key) {
        return key.equals(COOKIE);
    }
}
