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

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private Map<String, String> responseHeader;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        responseHeader = new HashMap<>();
    }

    public void addHeader( String key, String value ) {
        responseHeader.put(key, value);
    }

    public void sendRedirect(String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + responseHeader.getOrDefault("host", "http://localhost:8080") + url + " \r\n");
            dos.writeBytes("Set-Cookie: " + responseHeader.getOrDefault("Set-Cookie", "logined=false") + "; Path=/\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type:" + responseHeader.get("Accept") + "\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void response200Header() {
        this.response200Header(0);
    }

    public void response200Header(int bodyLength) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + bodyLength + "\r\n");
            dos.writeBytes("Set-Cookie: " + responseHeader.getOrDefault("Set-Cookie", "logined=false") + "; Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void response404() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
