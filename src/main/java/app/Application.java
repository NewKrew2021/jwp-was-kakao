package app;

import app.controllers.HelloController;
import webserver.constant.HttpMethod;
import webserver.Router;
import webserver.WebServer;

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

        router.addRoute(HttpMethod.GET, "/hello", HelloController.getHelloHandler);

        webServer.start(port);
    }

    public CountDownLatch getStartSignal() {
        return webServer.getStartSignal();
    }

}
