package webserver;

import annotation.web.Controller;
import dto.RequestHeader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static final String BASE_URL = "http://localhost:8080/index.html";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String request = "";
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                request += line + "\n";
                line = bufferedReader.readLine();
            }
            RequestHeader requestHeader = RequestParser.parseHeader(request);

            List<String> parameters = requestHeader.getParameters();

            if (requestHeader.getMethod().equals("POST")) {
                String body = bufferedReader.readLine();
                parameters = RequestParser.parseUserInfo(body);
            }

            User user = null;
            DataOutputStream dos = new DataOutputStream(out);
            if (requestHeader.getPath().equals("/user/create")) {
                user = new User(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
                response302Header(dos, BASE_URL);
                logger.debug(user.toString());
                return;
            }

            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestHeader.getPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
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

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
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
