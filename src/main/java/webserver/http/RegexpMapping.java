package webserver.http;

import webserver.http.controller.Controller;

import java.util.regex.Pattern;

class RegexpMapping implements HttpRequestMapping {
    private Pattern uriPattern;
    private HttpMethod method;
    private final Controller controller;

    RegexpMapping(String uriPatternRegexp, HttpMethod method, Controller controller) {
        this.uriPattern = Pattern.compile(uriPatternRegexp);
        this.method = method;
        this.controller = controller;
    }

    @Override
    public boolean matches(HttpRequest httpRequest) {
        return uriPattern.matcher(httpRequest.getPath()).matches() && method == httpRequest.getMethod();
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
