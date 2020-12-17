package webserver;

import webserver.constant.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {

    private class RouteInfo {
        public String method;

        public String path;

        public HttpHandler handler;

        public RouteInfo(String method, String path, HttpHandler handler) {
            this.method = method;
            this.path = path;
            this.handler = handler;
        }

        public boolean isMatched(String method, String target) {
            if (this.method.equals(method)) {
                // TODO optimize
                Pattern p = Pattern.compile(path);
                Matcher matcher = p.matcher(target);

                if (matcher.matches()) {
                    return true;
                }
            }

            return false;
        }
    }

    public List<RouteInfo> routeInfoList = new ArrayList<>();

    public void addRoute(String method, String path, HttpHandler handler) {
        routeInfoList.add(new RouteInfo(method, path, handler));
    }

    public HttpResponse route(String method, String target, HttpRequest req) throws Exception {
        HttpHandler matchedHandler = routeInfoList.stream()
                .filter(routePair -> routePair.isMatched(method, target))
                .map(routePair -> routePair.handler)
                .findFirst()
                .orElse(defaultNotFoundHandler);

        return matchedHandler.handle(method, target, req);
    }

    private final static HttpHandler defaultNotFoundHandler = (method, target, req) -> new HttpResponse(HttpStatus.NOT_FOUND);

}
