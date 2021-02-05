package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final HttpHeaders headers;
    private final DataOutputStream dos;

    public HttpResponse(DataOutputStream dos) {
        this.headers = new HttpHeaders();
        this.dos = dos;
    }

    public void forward(byte[] body) {
        headers.addHeader("Content-Length", Integer.toString(body.length));
        response200Header();
        responseBody(body);
    }

    public void sendRedirect(String url) {
        headers.addHeader("Location", "http://localhost:8080" + url);
        response302Header();
    }

    public void setCookie(String cookie) {
        headers.addHeader("Set-Cookie", "logined=" + cookie + "; Path=/");
    }

    public void addHeader(String headerName, String headerValue) {
        headers.addHeader(headerName, headerValue);
    }

    private void response200Header() {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(String.join("\r\n", headers.toStringList()));
            dos.writeBytes("\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header() {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.join("\r\n", headers.toStringList()));
            dos.writeBytes("\r\n");
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
