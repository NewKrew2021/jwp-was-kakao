package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            List<String> texts = IOUtils.readRequest(in);
            logger.debug(texts.toString());
            String[] token = texts.get(0).split(" ");

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = "Hello World".getBytes();
            if (token[1].equals("/index.html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK " + IOUtils.NEW_LINE);
            dos.writeBytes("Content-Type: text/html;charset=utf-8" + IOUtils.NEW_LINE);
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "" + IOUtils.NEW_LINE);
            dos.writeBytes("" + IOUtils.NEW_LINE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
