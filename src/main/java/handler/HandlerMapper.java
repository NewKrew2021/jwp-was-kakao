package handler;

import web.HttpRequest;
import webserver.HttpServlet;

import java.util.Arrays;
import java.util.List;

public class HandlerMapper {
    private static final List<HttpServlet> handlers = Arrays.asList(
            new CssHandler(),
            new FontsHandler(),
            new HtmlHandler(),
            new IcoHandler(),
            new JsHandler(),
            new UserCreateHandler(),
            new UserListHandler(),
            new UserLoginHandler()
    );

    public static HttpServlet map(HttpRequest httpRequest) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(httpRequest))
                .findFirst()
                .orElse(new NotAllowedMethodHandler());
    }
}
