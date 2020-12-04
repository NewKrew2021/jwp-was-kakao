package apps.slipp;

import apps.slipp.authentication.CookieAuthenticator;
import apps.slipp.controller.LoginController;
import apps.slipp.controller.SignUpController;
import apps.slipp.controller.UserListController;
import webserver.WebServer;
import webserver.WebServerProperties;
import webserver.http.*;
import webserver.http.template.TemplateEngine;

import java.io.IOException;

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
                WebServerProperties.builder()
                        .port(port)
                        .httpRequestDispatcher(getHttpRequestDispatcher())
                        .httpRequestPreProcessor(getHttpRequestPreProcessor())
                        .build()
        );
        webServer.start();
    }

    private HttpRequestPreProcessor getHttpRequestPreProcessor() {
        return new CookieAuthenticator("/user/list");
    }

    private HttpRequestDispatcher getHttpRequestDispatcher() {
        Controller staticResourceController = new ResourceController(new ClasspathResourceLoader("./static"));
        return new DefaultHttpRequestDispatcher(
                new PathRegexpMapping("\\/css\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/js\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/fonts\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/.+\\.html", HttpMethod.GET, new ResourceController(new ClasspathResourceLoader("./templates"))),
                new PathRegexpMapping("\\/user\\/create", HttpMethod.POST, new SignUpController()),
                new PathRegexpMapping("\\/user\\/login", HttpMethod.POST, new LoginController()),
                new PathRegexpMapping("\\/user\\/list", HttpMethod.GET, new UserListController(TemplateEngine.handlebars("/templates", ".html")))
        );
    }
}
