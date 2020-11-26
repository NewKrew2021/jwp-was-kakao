package webserver.http;

import webserver.http.dispatcher.HttpRequestMapping;

import java.util.regex.Pattern;

public class RegexpMapping implements HttpRequestMapping {
    private Pattern uriPattern;
    private HttpMethod method;
    private final Controller controller;

    public RegexpMapping(String uriPatternRegexp, HttpMethod method, Controller controller) {
        this.uriPattern = Pattern.compile(uriPatternRegexp);
        this.method = method;
        this.controller = controller;
    }

    @Override
    public boolean matches(HttpRequest httpRequest) {
        return uriPattern.matcher(httpRequest.getPath()).matches()
                && method == httpRequest.getMethod();
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
