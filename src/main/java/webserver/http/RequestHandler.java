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
    public static final String LOGIN_PAGE = "./login.html";

    private final Socket connection;
    private final HttpRequestDispatcher dispatcher;
    private final HttpRequestPreProcessor preProcessor;

    public RequestHandler(Socket connectionSocket, HttpRequestDispatcher requestDispatcher, HttpRequestPreProcessor preProcessor) {
        this.connection = connectionSocket;
        this.preProcessor = preProcessor;
        this.dispatcher = requestDispatcher;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        HttpRequest httpRequest;
        HttpResponse httpResponse = null;
        try {
            InputStream in = connection.getInputStream();
            OutputStream out = connection.getOutputStream();

            httpRequest = createHttpRequest(in);
            printAllRequestHeaders(httpRequest);
            httpResponse = new HttpResponse(out);

            preProcessor.execute(httpRequest, httpResponse);
            dispatcher.dispatch(httpRequest, httpResponse);

            printAllResponseHeaders(httpResponse);
            httpResponse.send();
        } catch (AuthenticationException e){
            logger.debug(e.getMessage());
            httpResponse.sendRedirect(LOGIN_PAGE);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());

            httpResponse.setStatus(HttpStatus.x500_InternalServerError);
            httpResponse.setBody(e.getMessage().getBytes());
            httpResponse.send();
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

    private HttpRequest createHttpRequest(InputStream in) {
        return new HttpRequestFactory().create(new InputStreamReader(in));
    }

    private void printAllRequestHeaders(HttpRequest httpRequest) {
        logger.debug("---- request-line ----");
        logger.debug(httpRequest.getRequestLine());
        logger.debug("---- request-header ----");
        httpRequest.getHeaders().forEach(it -> logger.debug(it.toString()));
        logger.debug("---- reqeust-body ----");
        logger.debug(httpRequest.getBody());
    }

    private void printAllResponseHeaders(HttpResponse httpResponse) {
        logger.debug("---- response-status-line ---");
        logger.debug(httpResponse.getStatusLine());
        logger.debug("---- response-header ----");
        httpResponse.getHeaders().forEach(it -> logger.debug(it.toString()));
    }


}
