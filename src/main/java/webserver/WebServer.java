package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private CountDownLatch startSignal;

    private Router router;

    public WebServer() {
        this.startSignal = new CountDownLatch(1);
        this.router = new Router();
    }

    public void start(int port) throws IOException {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            startSignal.countDown();

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, router));
                thread.start();
            }
        }
    }

    public Router getRouter() {
        return router;
    }

    public CountDownLatch getStartSignal() {
        return startSignal;
    }

}
