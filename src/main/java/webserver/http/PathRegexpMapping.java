package webserver.http;

import java.util.regex.Pattern;

public class PathRegexpMapping<T> implements HttpRequestMapping {
    private Pattern pathPattern;
    private HttpMethod method;
    private final T target;

    public PathRegexpMapping(String pathRegexp, HttpMethod method, T target) {
        this.pathPattern = Pattern.compile(pathRegexp);
        this.method = method;
        this.target = target;
    }

    @Override
    public boolean matches(HttpRequest httpRequest) {
        return pathPattern.matcher(httpRequest.getPath()).matches()
                && method == httpRequest.getMethod();
    }

    @Override
    public T getTarget() {
        return target;
    }
}
