package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String LOGIN_PAGE = "./login.html";

    private final Socket connection;
    private final HttpRequestDispatcher requestDispatcher;
    private final HttpRequestPreProcessor requestPreProcessor;
    private final ExceptionHandler exceptionHandler;

    public RequestHandler(Socket connectionSocket, HttpRequestDispatcher requestDispatcher, HttpRequestPreProcessor requestPreProcessor, ExceptionHandler exceptionHandler) {
        this.connection = connectionSocket;
        this.requestPreProcessor = requestPreProcessor;
        this.requestDispatcher = requestDispatcher;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        try {
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();

            httpRequest = new HttpRequestFactory().create(new InputStreamReader(in));
            httpResponse = new HttpResponse(out);

            requestPreProcessor.execute(httpRequest, httpResponse);
            requestDispatcher.dispatch(httpRequest, httpResponse);

        } catch ( Exception e ){
            exceptionHandler.handle(e, httpRequest, httpResponse);
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException("socket close 과정에 문제가 발생했습니다", e);
        }
    }

}
