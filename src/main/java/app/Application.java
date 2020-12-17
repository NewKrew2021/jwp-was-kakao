package app;

import app.controllers.StaticFileController;
import app.controllers.UserController;
import webserver.Router;
import webserver.WebServer;
import webserver.constant.HttpMethod;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Application {

    private static final int DEFAULT_PORT = 8080;

    private WebServer webServer;

    public static void main(String args[]) throws IOException {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        new Application().run(port);
    }

    public Application() {
        this.webServer = new WebServer();
    }

    public void run(int port) throws IOException {
        Router router = webServer.getRouter();

        router.addRoute(HttpMethod.POST, "/user/create", UserController.postSignUpHandler);
        router.addRoute(HttpMethod.POST, "/user/login", UserController.postLoginHandler);
        router.addRoute(HttpMethod.GET, "/user/list", UserController.getListHandler);
        router.addRoute(HttpMethod.GET, "/user/list.html", UserController.getListHandler);

        router.addRoute(HttpMethod.GET, "/(css|fonts|images|js)/.+", StaticFileController.getStaticFileHandler);
        router.addRoute(HttpMethod.GET, "/favicon.ico", StaticFileController.getStaticFileHandler);
        router.addRoute(HttpMethod.GET, "/.+\\.html", StaticFileController.getStaticTemplateFileHandler);

        webServer.start(port);
    }

    public CountDownLatch getStartSignal() {
        return webServer.getStartSignal();
    }

}
