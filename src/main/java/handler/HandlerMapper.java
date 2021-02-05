package handler;

import web.HttpRequest;
import webserver.HttpServlet;
import webserver.SessionManager;

import java.util.Arrays;
import java.util.List;

public class HandlerMapper {
    private final List<HttpServlet> handlers;

    public HandlerMapper(SessionManager sessionManager) {
        handlers = Arrays.asList(
                new CssHandler(),
                new FontsHandler(),
                new HtmlHandler(),
                new IcoHandler(),
                new JsHandler(),
                new UserCreateHandler(),
                new UserListHandler(sessionManager),
                new UserLoginHandler(sessionManager)
        );
    }

    public HttpServlet map(HttpRequest httpRequest) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(httpRequest))
                .findFirst()
                .orElseGet(NotAllowedMethodHandler::new);
    }
}
