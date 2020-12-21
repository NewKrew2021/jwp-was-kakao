package webserver.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestLine {
    private static final Pattern requestLinePattern = Pattern.compile(
            "(?<method>[A-Z]+)" +         // request method
            " +(?<path>/[^?# ]*)" +        // resource path (only path part)
            "(?:\\?(?<query>[^# ]+))?" +  // query string (optional)
            "(?:#(?<hash>[^ ]+))?" +      // hash (optional)
            "(?: +(?<version>.*))?"       // version (optional)
    );

    private final HttpMethod method;
    private final String path;
    private final String query;
    private final String hash;
    private final String version;

    public HttpRequestLine(HttpMethod method, String path, String query, String hash, String version) {
        this.method = method;
        this.path = path;
        this.query = query;
        this.hash = hash;
        this.version = version;
    }

    public static HttpRequestLine from(String requestLine) {
        Matcher matcher;

        if (Objects.isNull(requestLine) || !(matcher = requestLinePattern.matcher(requestLine)).find()) {
            throw new IllegalArgumentException("Unexpected Request-Line message");
        }

        return new HttpRequestLine(
                HttpMethod.valueOf(matcher.group("method")),
                matcher.group("path"),
                matcher.group("query"),
                matcher.group("hash"),
                matcher.group("version")
        );
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getHash() {
        return hash;
    }

    public String getVersion() {
        return version;
    }
}
