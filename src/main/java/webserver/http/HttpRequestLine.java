package webserver.http;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestLine {

    private String requestLineFormat = "(GET|POST)\\s(.+)\\s(.+)";
    private Pattern requestLinePattern = Pattern.compile(requestLineFormat);

    private QueryParser queryParser = new QueryParser();

    private HttpMethod method;
    private URI uri;
    private String httpVersion;
    private List<HttpRequestParam> params;

    public HttpRequestLine(String requestLineString){
        parse(requestLineString);
    }

    HttpRequestLine(String method, String uri, String httpVersion) {
        this.method = HttpMethod.valueOf(method);
        this.uri = URI.create(uri);
        this.httpVersion = httpVersion;
    }

    private void parse(String requestLineString) {
        Matcher matcher = requestLinePattern.matcher(requestLineString);
        if( !matcher.matches() ){
            throw new InvalidHttpRequestMessageException("Request-Line 형식이 올바르지 않습니다. ( request-line: " + requestLineString + ")");
        }
        method = HttpMethod.valueOf(matcher.group(1));
        uri = URI.create(matcher.group(2));
        httpVersion = matcher.group(3);

        parseQuery(uri.getQuery());
    }

    private void parseQuery(String query) {
        params = queryParser.parse(query);
    }


    public HttpMethod getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public List<HttpRequestParam> getParams() {
        return params;
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
