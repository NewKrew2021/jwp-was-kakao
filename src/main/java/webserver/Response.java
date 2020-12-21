package webserver;

import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private final String url;
    private final Map<String, String> header;
    private HttpStatus httpStatus;
    private byte[] body;

    private Response(String url) {
        this.url = url;
        this.header = new HashMap<>();
    }

    public static Response of(Request request) {
        return new Response(request.getURL());
    }

    public void forward() {
        httpStatus = HttpStatus.HTTP_OK;
        body = ResponseHandler.getBody(url);
    }

    public void sendRedirect(String path) {
        httpStatus = HttpStatus.HTTP_FOUND;
        header.put("Location", path);
        body = ResponseHandler.getBody(url);
    }

    public void response200Body(String body) {
        httpStatus = HttpStatus.HTTP_OK;
        this.body = body.getBytes();
    }

    public void setHeaderCookie(boolean isLogined) {
        header.put("Set-Cookie", "logined=" + isLogined + "; Path=/");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String descHttpStatusCode() {
        return httpStatus.descHttpStatusCode();
    }

    public List<String> getAddHttpDesc() {
        List<String> addHttpDesc = new ArrayList<>();

        header.forEach((key, value) -> addHttpDesc.add(String.format("%s: %s \r\n", key, value)));

        return addHttpDesc;
    }

    public byte[] getBody() {
        return ResponseHandler.getBody(url);
    }


    public void output(DataOutputStream dos) {
        if (isCSS()) {
            responseCSSHeader(dos, body.length);
            responseBody(dos, body);
        } else {
            responseHeader(dos, body.length);
            responseBody(dos, body);
        }
    }

    public boolean isCSS() {
        return url.startsWith("/css");
    }

    public void responseCSSHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            String format = String.format("HTTP/1.1 %s \r\n", descHttpStatusCode());
            dos.writeBytes(format);

            getAddHttpDesc().forEach(value -> {
                try {
                    dos.writeBytes(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

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
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
