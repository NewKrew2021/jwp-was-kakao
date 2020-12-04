package webserver;

import apps.slipp.authentication.CookieAuthenticator;
import apps.slipp.controller.LoginController;
import apps.slipp.controller.SignUpController;
import apps.slipp.controller.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ClasspathResourceLoader;
import webserver.http.*;
import webserver.http.template.TemplateEngine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private HttpRequestDispatcher requestDispatcher;
    private HttpRequestPreProcessor preProcessor;

    public WebServer() {
        Controller staticResourceController = new ResourceController(new ClasspathResourceLoader("./static"));
        requestDispatcher = new DefaultHttpRequestDispatcher(
                new PathRegexpMapping("\\/css\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/js\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/fonts\\/.+", HttpMethod.GET, staticResourceController),
                new PathRegexpMapping("\\/.+\\.html", HttpMethod.GET, new ResourceController(new ClasspathResourceLoader("./templates"))),
                new PathRegexpMapping("\\/user\\/create", HttpMethod.POST, new SignUpController()),
                new PathRegexpMapping("\\/user\\/login", HttpMethod.POST, new LoginController()),
                new PathRegexpMapping("\\/user\\/list", HttpMethod.GET, new UserListController(TemplateEngine.handlebars("/templates", ".html")))
        );
        preProcessor = new CookieAuthenticator("/user/list");
    }

    public void start(int port) throws IOException {
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            while (true) {
                Socket connection = listenSocket.accept();
                launchRequestHandler(connection);
            }
        }
    }

    private void launchRequestHandler(Socket connection) {
        new Thread(new RequestHandler(connection, requestDispatcher, preProcessor)).start();
    }

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        WebServer webServer = new WebServer();
        webServer.start(port);
    }
}
