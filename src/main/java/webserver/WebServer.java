package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8080;

    private WebServerProperties properties;

    public WebServer(WebServerProperties properties) {
        this.properties = properties;
    }

    public void start() throws IOException{
        start(properties.getPortOrDefault(DEFAULT_PORT));
    }

    public void start(int port) throws IOException {
        shouldUsable(port);

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            while (true) {
                Socket connection = listenSocket.accept();
                launchRequestHandler(connection);
            }
        }
    }

    private void shouldUsable(int port) {
        if( port <= 80 ) throw new RuntimeException("80 이하의 port 는 사용할 수 없습니다");
    }

    private void launchRequestHandler(Socket connection) {
        new Thread(new RequestHandler(connection, properties.getRequestDispatcher(), properties.getRequestPreProcessor())).start();
    }

}
