package webserver;

import handler.FontsHandler;
import handler.HandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.HttpRequest;
import web.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final SessionManager sessionManager;

    public RequestHandler(Socket connectionSocket, SessionManager sessionManager) {
        this.connection = connectionSocket;
        this.sessionManager = sessionManager;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            DataOutputStream dos = new DataOutputStream(out);

            HandlerMapper handlerMapper = new HandlerMapper(sessionManager);
            HttpServlet httpServlet = handlerMapper.map(httpRequest);
            if (httpServlet instanceof FontsHandler) {
                writeResponse(dos, httpServlet.service(httpRequest), StandardCharsets.ISO_8859_1);
            } else {
                writeResponse(dos, httpServlet.service(httpRequest));
            }
        } catch (IOException e) {

            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        writeResponse(dos, httpResponse, StandardCharsets.UTF_8);
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse, Charset charset) throws IOException {
        dos.write(httpResponse.toString().getBytes(charset));
        dos.flush();
    }
}
