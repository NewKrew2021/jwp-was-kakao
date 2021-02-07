package handler;

import web.HttpRequest;
import webserver.HttpServlet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerMapper {
    private static final List<HttpServlet> handlers;

    static {
        List<HttpServlet> httpServlets = Arrays.asList(
                new CssHandler(),
                new FontsHandler(),
                new HtmlHandler(),
                new IcoHandler(),
                new JsHandler(),
                new UserCreateHandler(),
                new UserListHandler(),
                new UserLoginHandler()
        );

        handlers = httpServlets.stream()
                .map(ExceptionHandler::new)
                .collect(Collectors.toList());
    }

    public static HttpServlet map(HttpRequest httpRequest) {
        return handlers.stream()
                .filter(handler -> handler.isSupport(httpRequest))
                .findFirst()
                .orElseGet(NotAllowedMethodHandler::new);
    }
}