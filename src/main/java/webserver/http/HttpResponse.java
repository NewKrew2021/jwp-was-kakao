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
    public static final HttpResponse _500_INTERNAL_SERVER_ERROR = new HttpResponse(HttpCode._500);

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpCode responseCode = HttpCode._200;
    private final Map<String, String> headers;
    private byte[] body;

    public HttpResponse() {
        headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.TEXT_HTML_UTF8);
    }

    public HttpResponse(HttpCode responseCode) {
        this();
        this.responseCode = responseCode;
    }

    public HttpResponse(HttpCode responseCode, byte[] body) {
        this(responseCode);
        this.body = body;
    }

    public void setResponseCode(HttpCode responseCode) {
        this.responseCode = responseCode;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void response(OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            writeResponseCodeHeader(dos);
            writeHeaders(dos);
            writeResponseBody(dos);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponseCodeHeader(DataOutputStream dos) throws IOException {
        String responseCodeHeader = String.format("HTTP/1.1 %s %s \r\n", responseCode.getStatusCode(), responseCode.getMessage());
        dos.writeBytes(responseCodeHeader);
    }

    private void writeHeaders(DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String header = String.format("%s: %s \r\n", entry.getKey(), entry.getValue());
            dos.writeBytes(header);
        }
    }

    private void writeResponseBody(DataOutputStream dos) throws IOException {
        if (body == null) {
            return;
        }
        dos.writeBytes("Content-Length: " +  body.length + "\r\n");
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
    }
}
