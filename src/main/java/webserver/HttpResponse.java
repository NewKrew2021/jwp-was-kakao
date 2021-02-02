package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PREFIX = "templates";
    private static final String STATIC_PREFIX = "static";

    DataOutputStream dos;
    Map<String, String> headers;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        byte[] response = readFile(path);
        response200Header(response.length);
        responseBody(response);
    }

    public void forwardBody(String body) {
        byte[] response = body.getBytes();
        response200Header(response.length);
        responseBody(response);
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            printHeader();
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 REDIRECTED\r\n");
            printHeader();
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader() throws IOException {
        this.headers.forEach((key, value) -> {
            try {
                dos.writeBytes(String.format("%s: %s\r\n", key, value));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private byte[] readFile(String path) throws IOException, URISyntaxException {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PREFIX + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + path);
    }

}
