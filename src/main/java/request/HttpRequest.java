package request;

import utils.ParseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    private final String URL = "url";

    private HttpMethod method;
    private String url;
    private String version;
    private String path;

    private Map<String, String> headers;
    private Map<String, String> bodys;

    public HttpRequest() {
    }

    private HttpRequest(List<String> requestLines) {
        headers = new HashMap<>();
        bodys = new HashMap<>();
        getMethodAndUrl(requestLines.get(0));
        getHeaders(requestLines);
//        getBody(requestLines);
    }

    public static HttpRequest of(List<String> requestLines) {
        return new HttpRequest(requestLines);
    }

    public void parseGetMethodBody() {
        if(ParseUtils.containRequestUrlRegex(url)) {
            bodys = ParseUtils.getParameters(ParseUtils.getParameterPairs(url));
        }
    }

    private void getHeaders(List<String> lines) {
        lines.stream()
                .skip(1)
                .limit(lines.size() - 2)
                .forEach(line -> headers.put(ParseUtils.parseHeaderKey(line), ParseUtils.parseHeaderValue(line)));
    }


    private void getMethodAndUrl(String line) {
        String[] lines = line.split(" ");
        method = HttpMethod.of(lines[0]);
        url = lines[1];
        version = lines[2];
        path = ParseUtils.getUrlPath(url);
    }

    public void parsePostMethodBody(String body){
        bodys = ParseUtils.getParameters(body);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getURL() {
        return URL;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getBodys() {
        return bodys;
    }
}
