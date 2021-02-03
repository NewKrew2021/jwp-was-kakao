package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos;

    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public void response200Header(String url, int lengthOfBodyContent) {
        addHeader("Content-Type", ContentType.of(url) + "; charset=utf-8");
        addHeader("Content-Length", String.valueOf(lengthOfBodyContent));
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            writeHeaders();
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

    public void sendRedirect(String url) {
        addHeader("Location", url);
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            writeHeaders();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String url) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(url);
            response200Header(url, body.length);
            responseBody(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeHeaders() throws IOException {
        headers.forEach((key, value) -> {
            try {
                dos.writeBytes(key + ": " + value + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        dos.writeBytes("\r\n");
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
