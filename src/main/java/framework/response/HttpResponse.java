package framework.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static framework.common.HttpHeaders.CONTENT_LENGTH;
import static framework.common.HttpHeaders.LOCATION;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String KEY_VALUE_REGEX = ": ";
    private static final String NEW_LINE_PREFIX = "\r\n";

    private final DataOutputStream dos;

    private ResponseStatus status;
    private final Map<String, String> headers;
    private byte[] body;

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
        body = new byte[0];
        headers = new HashMap<>();
    }

    public static HttpResponse of(DataOutputStream dos) {
        return new HttpResponse(dos);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void sendRedirect(String path) throws IOException {
        headers.put(LOCATION.getHeader(), path);
        status = ResponseStatus.FOUND;
        response();
    }

    public void responseBody(byte[] body) throws IOException {
        this.body = body;
        headers.put(CONTENT_LENGTH.getHeader(), String.valueOf(body.length));
        status = ResponseStatus.OK;
        response();
    }

    public void badRequest() throws IOException {
        status = ResponseStatus.BAD_REQUEST;
        response();
    }

    public void notFound() throws IOException {
        status = ResponseStatus.NOT_FOUND;
        response();
    }

    private void response() throws IOException {
        writeStatus();
        writeHeader();
        writeBody();
    }

    private void writeStatus() throws IOException {
        String statusMessage = HTTP_VERSION + " " +
                status.getCode() + " " +
                status.getMessage() + " " +
                NEW_LINE_PREFIX;
        dos.writeBytes(statusMessage);
    }

    private void writeHeader() throws IOException {
        for (String key : headers.keySet()) {
            String header = key +
                    KEY_VALUE_REGEX +
                    headers.get(key) +
                    NEW_LINE_PREFIX;
            dos.writeBytes(header);
        }
        dos.writeBytes(NEW_LINE_PREFIX);
    }

    private void writeBody() throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
