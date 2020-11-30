package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.RequestPathUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String DEFAULT_RESPONSE = "Hello World";
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            List<String> requestHeaders = getRequestHeaders(bufferedReader);
            String requestPath = RequestPathUtils.getRequestPath(requestHeaders.get(0));
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getBody(requestPath);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(String requestPath) {
        logger.info("requestPath : {}", requestPath);
        try {
            if ("/".equals(requestPath)) {
                return DEFAULT_RESPONSE.getBytes();
            }
            return FileIoUtils.loadFileFromClasspath(RequestPathUtils.getResourcePath(requestPath));
        } catch (IOException | URISyntaxException ex) {
            logger.error(ex.getMessage());
        }
        return DEFAULT_RESPONSE.getBytes();
    }

    private List<String> getRequestHeaders(BufferedReader bufferedReader) {
        List<String> headers = new ArrayList<>();
        try {
            String line = "";
            while (!"".equals(line = bufferedReader.readLine())) {
                System.out.println(line); //헤더 출력
                headers.add(line);
            }
        }catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return headers;
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
