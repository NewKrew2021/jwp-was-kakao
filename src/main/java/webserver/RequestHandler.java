package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.constant.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Router router;

    private final Socket connection;

    public RequestHandler(Socket connectionSocket, Router router) {
        this.connection = connectionSocket;
        this.router = router;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(in, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(InputStream in, OutputStream out) {
        try {
            HttpRequest req = new HttpRequest(in);

            Optional<HttpResponse> resp = router.route(req.getMethod(), req.getTarget(), req);

            HttpResponse finalResp = resp.orElse(HttpResponse.Builder.prepare()
                    .status(HttpStatus.NOT_FOUND)
                    .build());

            writeResponse(out, finalResp);
        } catch (HttpException e) {
            logger.warn("error during process", e);

            writeResponse(out, e.toHttpResponse());
        } catch (Exception e) {
            logger.error("unexpected error during process", e);

            writeResponse(out, HttpException.internalServerError("sorry").toHttpResponse());
        }
    }

    private void writeResponse(OutputStream out, HttpResponse resp) {
        try {
            DataOutputStream dos = new DataOutputStream(out);

            byte[] payload = resp.toString().getBytes();
            dos.write(payload, 0, payload.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
