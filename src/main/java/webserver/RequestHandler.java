package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final RequestMapping requestMapping;
    private final ResponseHandler responseHandler;

    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping) {
        this(connectionSocket, requestMapping, new ResponseHandler());
    }
    public RequestHandler(Socket connectionSocket, RequestMapping requestMapping, ResponseHandler responseHandler) {
        this.connection = connectionSocket;
        this.requestMapping = requestMapping;
        this.responseHandler = responseHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = parseRequest(in);
            String requestURI = httpRequest.getRequestURI();
            logger.debug("requestURI: {}", requestURI);

            Response response = handleRequest(httpRequest);

            response(new DataOutputStream(out), response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private Response handleRequest(HttpRequest httpRequest) throws Exception {
        return Optional.ofNullable(requestMapping.getController(httpRequest.getRequestURI()))
                .orElseGet(StaticContentController::new)
                .execute(httpRequest);
    }

    private HttpRequest parseRequest(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF_8));
        RequestParser requestParser = new RequestParser(bufferedReader);
        return requestParser.parse();
    }

    void response(DataOutputStream dos, Response response) throws IOException {
        responseHandler.response(dos, response);
    }
}
