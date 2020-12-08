package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.*;
import webserver.http.session.HttpSessionManager;
import webserver.http.session.SessionIdSetter;
import webserver.http.session.SimpleHttpSessionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);

    private static final int DEFAULT_PORT = 8080;
    private final HttpRequestDispatcher requestDispatcher;
    private final HttpRequestPreProcessor requestPreProcessor;
    private final ExceptionHandler exceptionHandler;
    private final HttpSessionManager sessionManager;

    private int port;

    public WebServer(WebServerConfig config) {
        if( config == null ) throw new IllegalArgumentException("WebServerConfig 는 필수 입력값입니다");

        this.port = config.getPortOrDefault(DEFAULT_PORT);
        this.sessionManager = createSessionManager(config);
        this.requestDispatcher = new DefaultHttpRequestDispatcher(new DefaultHttpResponseHandler(new SessionIdSetter(this.sessionManager)), config.getRequestMappings());
        this.requestPreProcessor = config.getRequestPreProcessor();
        this.exceptionHandler = new DefaultExceptionHandler(new ExceptionHandlerResolver(config.getExceptionHandlers()));
    }

    private HttpSessionManager createSessionManager(WebServerConfig config) {
        if( config.isEnableSession() )
            return new SimpleHttpSessionManager();
        return null;
    }

    public void start() throws IOException{
        start(port);
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
        new Thread(
                new RequestHandler(
                        connection,
                        requestDispatcher,
                        requestPreProcessor,
                        exceptionHandler,
                        sessionManager)
        ).start();
    }

}
