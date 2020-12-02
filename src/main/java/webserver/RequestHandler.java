package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.model.HttpStatus;
import webserver.model.Request;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            Request request = new Request(in);
            DataOutputStream dos = new DataOutputStream(out);

            if (request.getPath().equals("/user/create")) {
                handleUserCreate(request, dos);
                return;
            }

            handleStatic(request, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleUserCreate(Request request, DataOutputStream dos) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
    }

    private void handleStatic(Request request, DataOutputStream dos) throws IOException {
        try {
            byte[] body = request.getRequestedResource();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (URISyntaxException | NullPointerException e) {
            responseHeaderOnly(dos, HttpStatus.NOT_FOUND);
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

    private void responseHeaderOnly(DataOutputStream dos, HttpStatus status) {
        ResponseWriter writer = new ResponseWriter(dos);
        writer.format("HTTP/1.1 %d %s", status.getCode(), status.getMessage());
        writer.println();
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
