package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

import dto.RequestHeader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

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
            while(line != null && !line.equals("")) {
                request += line + "\n";
                line = bufferedReader.readLine();
            }
            RequestHeader requestHeader = RequestParser.parseHeader(request);

            List<String> parameters = requestHeader.getParameters();

            if(requestHeader.getMethod().equals("POST")) {
                String body = bufferedReader.readLine();
                parameters = RequestParser.parseUserInfo(body);
            }

            User user = null;
            if (requestHeader.getPath().equals("/user/create")) {
                user = new User(parameters.get(0), parameters.get(1), parameters.get(2), parameters.get(3));
                logger.debug(user.toString());
            }

            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestHeader.getPath());

            DataOutputStream dos = new DataOutputStream(out);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
