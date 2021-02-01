package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import controller.UserController;
import domain.Dispatcher;
import domain.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final Dispatcher dispatcher = Dispatcher.getInstance();
    private final UserController userController = UserController.getInstance();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        userController.registerAll();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            List<String> lines = new ArrayList<>();
            String line = br.readLine();
            while(!line.equals("")) {
                lines.add(line);
                line = br.readLine();
            }

            Request request = new Request(lines);

            byte[] body = "No Page".getBytes();


            if (FileIoUtils.pathIsFile(request.getUrlPath())) {
                body = FileIoUtils.loadFileFromUrlPath(request.getUrlPath());
            } else {
                body = dispatcher.run(request);
            }


            DataOutputStream dos = new DataOutputStream(out);

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
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
