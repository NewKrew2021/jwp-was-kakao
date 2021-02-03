package webserver.domain;

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
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String LOCATION = "Location";
    private static final String HTTP_200_OK = "HTTP/1.1 200 OK \r\n";
    private static final String HTTP_302_FOUND = "HTTP/1.1 302 Found \r\n";
    private static final String HTTP_404_NOT_FOUND = "HTTP/1.1 404 NOT Found \r\n";

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private Map<String, String> responseHeader;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        responseHeader = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        responseHeader.put(key, value);
    }

    public void sendRedirect(String url) {
        try {
            dos.writeBytes(HTTP_302_FOUND);
            dos.writeBytes(LOCATION + ": " + url + " \r\n");
            dos.writeBytes(SET_COOKIE + ": " + responseHeader.get(SET_COOKIE) + "; Path=/\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forward(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes(HTTP_200_OK);
            dos.writeBytes(CONTENT_TYPE + ": " + responseHeader.get(CONTENT_TYPE) + "\r\n");
            dos.writeBytes(CONTENT_LENGTH + ": " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void response200Header(int bodyLength) {
        try {
            dos.writeBytes(HTTP_200_OK);
            dos.writeBytes(CONTENT_TYPE + ": " + responseHeader.get(CONTENT_TYPE) + "\r\n");
            dos.writeBytes(CONTENT_LENGTH + ": " + bodyLength + "\r\n");
            dos.writeBytes(SET_COOKIE + ": " + responseHeader.get(SET_COOKIE) + "; Path=/\r\n");
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
            dos.writeBytes(HTTP_404_NOT_FOUND);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
