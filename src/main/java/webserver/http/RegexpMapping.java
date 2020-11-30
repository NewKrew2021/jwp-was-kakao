package webserver.http;

import java.util.regex.Pattern;

public class RegexpMapping<T> implements HttpRequestMapping {
    private Pattern uriPattern;
    private HttpMethod method;
    private final T target;

    public RegexpMapping(String uriPatternRegexp, HttpMethod method, T target) {
        this.uriPattern = Pattern.compile(uriPatternRegexp);
        this.method = method;
        this.target = target;
    }

    @Override
    public boolean matches(HttpRequest httpRequest) {
        return uriPattern.matcher(httpRequest.getPath()).matches()
                && method == httpRequest.getMethod();
    }

    @Override
    public T getTarget() {
        return target;
    }
}
