package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CHARSET_UTF_8 = "charset=utf-8";
    public static final String HTTP_1_1_200_OK = "HTTP/1.1 200 OK \r\n";
    public static final String LOCATION = "Location";
    public static final String HTTP_1_1_302_FOUND = "HTTP/1.1 302 Found \r\n";
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private final DataOutputStream dos;

    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    private void response200Header(String url, int lengthOfBodyContent) {
        addHeader(CONTENT_TYPE, ContentType.of(url).getMimeType() + "; " + CHARSET_UTF_8);
        addHeader(CONTENT_LENGTH, String.valueOf(lengthOfBodyContent));
        try {
            dos.writeBytes(HTTP_1_1_200_OK);
            writeHeaders();
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

    public void sendRedirect(String url) {
        addHeader(LOCATION, url);
        try {
            dos.writeBytes(HTTP_1_1_302_FOUND);
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
            logger.error(e.getMessage());
        }
    }

    public void forward(String url, byte[] body) {
        try {
            response200Header(url, body.length);
            responseBody(body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders() throws IOException {
        headers.forEach((key, value) -> {
            try {
                dos.writeBytes(key + ": " + value + "\r\n");
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
        dos.writeBytes("\r\n");
    }

}
