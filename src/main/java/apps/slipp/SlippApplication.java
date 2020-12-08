package apps.slipp;

import apps.slipp.authentication.CookieAuthenticator;
import apps.slipp.controller.LoginController;
import apps.slipp.controller.SignUpController;
import apps.slipp.controller.UserListController;
import webserver.WebServer;
import webserver.WebServerConfig;
import webserver.http.*;
import webserver.http.template.TemplateEngine;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlippApplication {

    public static void main(String args[]) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0)
            port = Integer.parseInt(args[0]);

        new SlippApplication().start(port);
    }

    private WebServer webServer;

    void start(int port) throws IOException {
        webServer = new WebServer(
                WebServerConfig.configurer( it -> {
                    it.setPort(port);
                    it.setRequestMappings(getRequestMappings());
                    it.setEnableSession(true);
                    it.setRequestPreProcessor(getHttpRequestPreProcessor());
                    it.setExceptionHandlers(getExceptionHandlers());
                    return it;
                })
        );
        webServer.start();
    }

    private Map<Class, ExceptionHandler> getExceptionHandlers() {
        Map<Class, ExceptionHandler> handlers = new HashMap<>();

        handlers.put(AuthenticationException.class, (e, httpRequest, httpResponse) -> {
            httpResponse.sendRedirect("./login.html");
        });
        handlers.put(InvalidHttpRequestMessageException.class,  (e, httpRequest, httpResponse) -> {
            httpResponse.setStatus(HttpStatus.x400_BadRequest);
            httpResponse.send();
        });
        handlers.put(HttpStatusCodeException.class, (ExceptionHandler<HttpStatusCodeException>) (e, httpRequest, httpResponse) -> {
            httpResponse.setStatus(e.getStatus());
            httpResponse.send();
        });
        return handlers;
    }

    private List<HttpRequestMapping> getRequestMappings() {
        Controller staticResourceController = new ResourceController(new ClasspathResourceLoader("./static"));
        return Arrays.asList(
                new PathRegexpMapping("\\/css\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/js\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/fonts\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/.+\\.html", HttpMethod.GET, new ResourceController(new ClasspathResourceLoader("./templates"))),
                new PathRegexpMapping("\\/user\\/create", HttpMethod.POST, new SignUpController()),
                new PathRegexpMapping("\\/user\\/login", HttpMethod.POST, new LoginController()),
                new PathRegexpMapping("\\/user\\/list", HttpMethod.GET, new UserListController(TemplateEngine.handlebars("/templates", ".html"))));
    }

    private HttpRequestPreProcessor getHttpRequestPreProcessor() {
        return new CookieAuthenticator("/user/list");
    }
}
