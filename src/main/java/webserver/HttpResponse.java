package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Map<String, String> header;
    private DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        header = new HashMap<>();
        this.dos = dos;
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public void forward(byte[] body) {
        addHeader("Content-Length", Integer.toString(body.length));
        response200Header();
        responseBody(body);
    }

    public void sendRedirect(String url) {
        addHeader("Location", "http://localhost:8080" + url);
        response302Header();
    }

    public void setCookie(String cookie) {
        addHeader("Set-Cookie", "logined=" + cookie + "; Path=/");
    }

    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            for (String key : header.keySet()) {
                dos.writeBytes(key + ": " + header.get(key) + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header() {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            for (String key : header.keySet()) {
                dos.writeBytes(key + ": " + header.get(key) + "\r\n");
            }
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
}
