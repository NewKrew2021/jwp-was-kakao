package webserver;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpRequestDispatcher;
import webserver.http.HttpRequestFactory;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private HttpRequestDispatcher httpRequestDispatcher;

    private DataBase db;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpRequestDispatcher = new HttpRequestDispatcher();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = createHttpRequest(in);
            printAllRequestHeaders(httpRequest);
            HttpResponse httpResponse = new HttpResponse(out);
            httpRequestDispatcher.dispatch(httpRequest, httpResponse);
            httpResponse.send();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest createHttpRequest(InputStream in) {
        return new HttpRequestFactory().create(new InputStreamReader(in));
    }

    private void printAllRequestHeaders(HttpRequest httpRequest) {
        logger.debug("---- request line ----");
        logger.debug(httpRequest.getRequestLine());
        logger.debug("---- request header ----");
        httpRequest.getHeaders().forEach(it -> logger.debug(it.toString()));
    }

}
