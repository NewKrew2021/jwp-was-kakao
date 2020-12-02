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

            httpRequest = new HttpRequestFactory().create(new InputStreamReader(in));
            httpResponse = new HttpResponse(out);

            preProcessor.execute(httpRequest, httpResponse);
            dispatcher.dispatch(httpRequest, httpResponse);

            httpResponse.send();
        } catch (AuthenticationException e) {
            logger.debug(e.getMessage());
            httpResponse.sendRedirect(LOGIN_PAGE);
        } catch (HttpStatusCodeException e ){
            httpResponse.setStatus(e.getStatus());
            httpResponse.send();
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

}
