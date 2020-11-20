package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.RequestUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = bufferedReader.readLine();
            String path = RequestUtil.getRequestPath(line);
            printRequestHeader(bufferedReader, line);
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getBodyBytes(path);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printRequestHeader(BufferedReader bufferedReader, String line) throws IOException {
        while (!"".equals(line)) {
            if (line == null) {
                break;
            }
            System.out.println(line);
            line = bufferedReader.readLine();
        }
    }

    private byte[] getBodyBytes(String path) {
        try {
            return FileIoUtils.loadFileFromClasspath(getResourcePath(path));
        } catch (Exception e) {
            e.printStackTrace();
            return "Hello World".getBytes();
        }
    }

    private String getResourcePath(String path) {
        if (path.startsWith("/js") ||
                path.startsWith("/css") ||
                path.startsWith("/fonts") ||
                path.startsWith("/images")
        ) {
            return "./static" + path;
        }
        return "./templates" + path;
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
