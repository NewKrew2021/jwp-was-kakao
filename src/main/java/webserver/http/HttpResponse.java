package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    public static final HttpResponse _200_OK = new HttpResponse(HttpCode._200);
    public static final HttpResponse _404_NOT_FOUND = new HttpResponse(HttpCode._404);
    public static final HttpResponse _405_METHOD_NOT_ALLOWED = new HttpResponse(HttpCode._405);

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpCode responseCode = HttpCode._200;
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public HttpResponse() {}

    public HttpResponse(HttpCode responseCode) {
        this.responseCode = responseCode;
    }

    public HttpResponse(HttpCode responseCode, byte[] body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    public HttpResponse(HttpCode responseCode, Map<String, String> headers) {
        this.responseCode = responseCode;
        this.headers = headers;
    }

    public void setResponseCode(HttpCode responseCode) {
        this.responseCode = responseCode;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void response(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        writeResponseCodeHeader(dos);

        writeHeaders(dos);

        if (body != null) {
            writeResponseBody(dos);
        }
    }

    private void writeResponseCodeHeader(DataOutputStream dos) {
        try {
            String responseCodeHeader = String.format("HTTP/1.1 %s %s \r\n", responseCode.getStatusCode(), responseCode.getMessage());
            dos.writeBytes(responseCodeHeader);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(DataOutputStream dos) {
        headers.forEach((key, value) -> {
            String header = String.format("%s: %s \r\n", key, value);
            try {
                dos.writeBytes(header);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    private void writeResponseBody(DataOutputStream dos) {
        try {
            // TODO : css 등 Content-Type 구분 필요
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " +  body.length + "\r\n");
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
