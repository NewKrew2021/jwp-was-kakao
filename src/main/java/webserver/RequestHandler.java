package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Router router;

    private final Socket connection;

    public RequestHandler(Socket connectionSocket, Router router) {
        this.connection = connectionSocket;
        this.router = router;
    }

    public void run() {
        logger.debug("New connection! {}:{}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String firstLine = null;

            // TODO keep alive counter, timeout

            while (connection.isConnected() && !connection.isClosed() &&
                    (firstLine = br.readLine()) != null) {
                handleMessage(firstLine, br, out);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                logger.warn("fail to close connection {}:{}", connection.getInetAddress(), connection.getPort());
            }
        }

        logger.debug("Close connection! {}:{}", connection.getInetAddress(),
                connection.getPort());
    }

    private void handleMessage(String firstLine, BufferedReader br, OutputStream out) throws Exception {
        try {
            HttpRequest req = new HttpRequest(firstLine, br);

            HttpResponse resp = router.route(req.getMethod(), req.getTarget(), req);

            writeResponse(out, resp);
        } catch (HttpException e) {
            logger.warn("error during process", e);

            writeResponse(out, e.toHttpResponse());
        } catch (Exception e) {
            logger.error("unexpected error during process", e);

            writeResponse(out, HttpException.internalServerError("sorry").toHttpResponse());

            throw e;
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
