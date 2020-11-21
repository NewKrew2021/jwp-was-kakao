package webserver.request;

import utils.RequestParser;

import java.util.List;
import java.util.Map;

public class RequestHeader {

    private final List<String> lines;

    private RequestHeader(List<String> lines) {
        this.lines = lines;
    }

    public static RequestHeader of(List<String> lines) {
        return new RequestHeader(lines);
    }

    @Override
    public String toString() {
        return String.join("\n", lines);
    }

    public RequestPath getPath() {
        return new RequestPath(RequestParser.getRequestPath(lines.get(0)));
    }

    public Map<String, String> getParams() {
        return RequestParser.getRequestParams(lines.get(0));
    }

    public String getMethod() {
        return RequestParser.getMethod(lines.get(0));
    }

    public Integer getContentLength() {
        return lines.stream()
                .filter(line -> line.contains("Content-Length"))
                .map(RequestParser::getContentLength)
                .findFirst()
                .orElse(null);
    }

    public String getHost() {
        return lines.stream()
                .filter(line -> line.contains("Host"))
                .map(RequestParser::getHost)
                .findFirst()
                .orElse(null);
    }

    public boolean isLogined() {
        return lines.stream()
                .filter(line -> line.contains("Cookie"))
                .map(line -> line.contains("logined=true"))
                .findFirst()
                .orElse(false);
    }
}
