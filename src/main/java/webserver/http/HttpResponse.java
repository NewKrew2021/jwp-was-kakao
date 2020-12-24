package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final Map<String, String> responseHeaders;
    private final DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        this.dos = new DataOutputStream(out);
        this.responseHeaders = new HashMap<>();
    }

    public void addHeaderValue(String key, String value) {
        responseHeaders.put(key, value);
    }

    private void responseHeader() {
        try {
            for (String key : responseHeaders.keySet()) {
                String value = responseHeaders.get(key);
                String header = key + ": " + value + "\r\n";

                dos.writeBytes(header);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    public void response200Header(int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            responseHeader();
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

    public void response404Header() {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            responseHeader();
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String location) {

        try {
            dos.writeBytes("HTTP/1.1 302 Found " + "\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            responseHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
