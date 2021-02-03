package domain;

import org.springframework.http.HttpMethod;

public class HttpRequestStartLine {
    public static final String LINE_DELIMITER = " ";
    public static final String PATH_DELIMITER = "?";
    public static final String PATH_DELIMETER_REGEX = "\\?";
    private HttpMethod method;
    private String url;
    private String version;
    private String parameter = "";

    public HttpRequestStartLine(String method, String url, String version) {
        this.method = HttpMethod.resolve(method);
        this.url = url;
        this.version = version;
    }

    public HttpRequestStartLine(String method, String url, String parameter, String version) {
        this.method = HttpMethod.resolve(method);
        this.url = url;
        this.parameter = parameter;
        this.version = version;
    }

    public static HttpRequestStartLine of(String line) {
        String[] methodPathVersion = line.split(LINE_DELIMITER);
        if (methodPathVersion[1].contains(PATH_DELIMITER)) {
            String[] urlParameter = methodPathVersion[1].split(PATH_DELIMETER_REGEX);
            return new HttpRequestStartLine(methodPathVersion[0], urlParameter[0], urlParameter[1], methodPathVersion[2]);
        }
        return new HttpRequestStartLine(methodPathVersion[0], methodPathVersion[1], methodPathVersion[2]);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public boolean hasParameter() {
        return !parameter.isEmpty();
    }

    public String getParameter() {
        return parameter;
    }

}
