package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_BODY = "Hello World";
    private static final String USER_CREATE_PATH = "/user/create";

    private Socket connection;
    private UserService userService;


    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        userService = new UserService();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            RequestHeader requestHeader = RequestHeader.of(readRequestHeader(bufferedReader));
            print(requestHeader);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getResponse(requestHeader);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void print(RequestHeader requestHeader) {
        System.out.println(requestHeader);
        System.out.println();
    }

    private List<String> readRequestHeader(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            lines.add(line);
            line = bufferedReader.readLine();
        }
        return lines;
    }

    private byte[] getResponse(RequestHeader header) {
        try {
            if (USER_CREATE_PATH.equals(header.getPath())) {
                return userService.addUser(header.getParams()).toString().getBytes();
            }
            return FileIoUtils.loadFileFromClasspath(header.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return DEFAULT_BODY.getBytes();
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
