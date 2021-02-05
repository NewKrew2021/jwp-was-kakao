package handler;

import web.HttpRequest;
import webserver.HttpServlet;
import webserver.UuidSessionManager;

import java.util.Arrays;
import java.util.List;

public class HandlerMapper {
    private final List<HttpServlet> handlers;

    public HandlerMapper(UuidSessionManager uuidSessionManager) {
        handlers = Arrays.asList(
                new CssHandler(),
                new FontsHandler(),
                new HtmlHandler(),
                new IcoHandler(),
                new JsHandler(),
                new UserCreateHandler(),
                new UserListHandler(uuidSessionManager),
                new UserLoginHandler(uuidSessionManager)
        );
    }

    public HttpServlet map(HttpRequest httpRequest) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(httpRequest))
                .findFirst()
                .orElseGet(NotAllowedMethodHandler::new);
    }
}
