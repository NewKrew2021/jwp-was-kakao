package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.model.Request;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            handle(new Request(in),  new DataOutputStream(out));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handle(Request request, DataOutputStream dos) {
        try {
            byte[] body = request.getRequestedResource();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            // TODO: response with 5xx
            e.printStackTrace();
        } catch (URISyntaxException | NullPointerException e) {
            response404Header(dos);
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404Header(DataOutputStream dos) {
        ResponseWriter writer = new ResponseWriter(dos);
        writer.println("HTTP/1.1 404 NOT FOUND");
        writer.println();
        writer.flush();
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    static class ResponseWriter extends PrintWriter {
        ResponseWriter(OutputStream out) {
            super(out, false);
        }

        @Override
        public void println() {
            write("\r\n");
        }
    }
}
