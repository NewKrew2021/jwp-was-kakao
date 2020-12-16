package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {

    private class RoutePair {
        public String method;

        // TODO support regex?
        public String path;

        public RoutePair(String method, String path) {
            this.method = method;
            this.path = path;
        }

        @Override
        public int hashCode() {
            return method.hashCode() + path.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof RoutePair)) {
                return false;
            }

            RoutePair rhs = (RoutePair)obj;

            return rhs.method.equals(rhs.method) &&
                    rhs.path.equals(rhs.path);
        }
    }

    public Map<RoutePair, HttpHandler> routePairHttpHandlerMap = new HashMap<>();

    public void addRoute(String method, String path, HttpHandler handler) {
        routePairHttpHandlerMap.put(new RoutePair(method, path), handler);
    }

    public Optional<HttpResponse> route(String method, String target, HttpRequest req) {
        HttpHandler httpHandler = routePairHttpHandlerMap.get(new RoutePair(method, target));

        if (httpHandler != null) {
            HttpResponse resp = httpHandler.handle(method, target, req);
            return Optional.of(resp);
        }

        return Optional.empty();
    }

}
