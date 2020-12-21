package webserver.model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String CONTENT_TYPE   = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String SET_COOKIE     = "Set-Cookie";
    private static final String LOCATION       = "Location";

    private final OutputStream out;
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void sendHeader(HttpStatus status) {
        PrintWriter writer = new PrintWriter(out, false) {
            @Override
            public void println() {
                write("\r\n");
            }
        };
        writer.format("HTTP/1.1 %d %s", status.getCode(), status.getMessage());
        writer.println();
        headers.forEach((k, v) -> writer.println(String.join(": ", k, v)));
        writer.println();
        writer.flush();
    }

    public void sendBody(byte[] body) throws IOException {
        out.write(body, 0, body.length);
        out.flush();
    }

    public void sendOk(ContentType type, byte[] body) throws IOException {
        setContentType(type);
        setContentLength(body.length);
        sendHeader(HttpStatus.OK);
        sendBody(body);
    }

    public void sendFound(String location) {
        setLocation(location);
        sendHeader(HttpStatus.FOUND);
    }

    public void setContentType(ContentType type) {
        headers.put(CONTENT_TYPE, type.getMimeType());
    }

    public void setContentLength(int length) {
        headers.put(CONTENT_LENGTH, String.valueOf(length));
    }

    public void setCookie(String key, String value) {
        headers.put(SET_COOKIE, String.format("%s=%s; Path=/", key, value));
    }

    public void setLocation(String location) {
        headers.put(LOCATION, location);
    }
}
