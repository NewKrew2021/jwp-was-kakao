package webserver;

import dto.RequestValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private URIFactory uriFactory;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.uriFactory = new URIFactory();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                     connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            RequestValue requestValue = RequestValue.of(in);
            requestValue.printRequest();

            Request request = Request.of(requestValue);
            Response response = uriFactory.create(request);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = ResponseHandler.getBody(requestValue.getURL());

            responseHeader(dos, response);
            responseContent(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    private void responseHeader(DataOutputStream dos, Response response) {
        try {
            String format = String.format("HTTP/1.1 %s \r\n", response.descHttpStatusCode());
            dos.writeBytes(format);

            response.getAddHttpDesc().forEach(value -> {
                try {
                    dos.writeBytes(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseContent(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
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
