package webserver.http;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestLine {

    private String requestLineFormat = "(GET|PUT|POST|DELETE)\\s(.+)\\s(.+)";
    private Pattern requestLinePattern = Pattern.compile(requestLineFormat);

    private String method;
    private String uri;
    private String httpVersion;

    public HttpRequestLine(String requestLineString){
        parse(requestLineString);
    }

    private void parse(String requestLineString) {
        Matcher matcher = requestLinePattern.matcher(requestLineString);
        if( !matcher.matches() ){
            throw new InvalidHttpRequestMessageException("Request-Line 형식이 올바르지 않습니다. ( request-line: " + requestLineString + ")");
        }
        method = matcher.group(1);
        uri = matcher.group(2);
        httpVersion = matcher.group(3);
    }

    HttpRequestLine(String method, String uri, String httpVersion) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1} {2}", method, uri, httpVersion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpRequestLine that = (HttpRequestLine) o;
        return Objects.equals(method, that.method) &&
                Objects.equals(uri, that.uri) &&
                Objects.equals(httpVersion, that.httpVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, uri, httpVersion);
    }
}
