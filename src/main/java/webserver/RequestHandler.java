package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.StaticContentController;
import webserver.http.HttpRequest;
import webserver.http.Response;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static webserver.http.HttpRequest.SESSION_ID;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestMapping requestMapping;
    private static final ResponseHandler RESPONSE_HANDLER = new ResponseHandler();

    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
        this.connection = connectionSocket;
        this.requestMapping = requestMapping;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = parseRequest(in);
            String requestURI = httpRequest.getRequestURI();
            logger.debug("requestURI: {}", requestURI);

            Response response = handleRequest(httpRequest);

            addSessionHeader(httpRequest, response);

            RESPONSE_HANDLER.handleResponse(new DataOutputStream(out), response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void addSessionHeader(HttpRequest httpRequest, Response response) {
        if (!httpRequest.getCookies().findCookieByName(SESSION_ID).isPresent()) {
            response.setHeaders("Set-Cookie: session_id=" + httpRequest.getSession().getId() + "; Path=/");
        }
    }

    private HttpRequest parseRequest(InputStream in) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestParser requestParser = new RequestParser(bufferedReader);
        return requestParser.parse();
    }

    private Response handleRequest(HttpRequest httpRequest) throws Exception {
        return Optional.ofNullable(requestMapping.getController(httpRequest.getMethod(), httpRequest.getRequestURI()))
                .orElseGet(StaticContentController::new)
                .execute(httpRequest);
    }

}
