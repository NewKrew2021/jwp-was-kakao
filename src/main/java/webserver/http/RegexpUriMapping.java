package webserver.http;

import webserver.http.controller.Controller;

import java.util.regex.Pattern;

class RegexpUriMapping implements UriMapping {
    private Pattern uriPattern;
    private final Controller controller;

    RegexpUriMapping(String uriPatternRegexp, Controller controller) {
        this.uriPattern = Pattern.compile(uriPatternRegexp);
        this.controller = controller;
    }

    @Override
    public boolean matches(String uri) {
        return uriPattern.matcher(uri).matches();
    }

    @Override
    public Controller getController() {
        return controller;
    }
}
