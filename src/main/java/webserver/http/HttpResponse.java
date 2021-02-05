package webserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private String httpStatus = "HTTP/1.1 404 NOT FOUND";
    private Map<String, String> headers = new HashMap<>();
    private byte[] body;

    public void setStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setBody(byte[] body, String mimeType) throws IOException, URISyntaxException {
        this.body = body;
        headers.put("Content-Type", mimeType);
        headers.put("Content-Length", String.valueOf(body.length));
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(String name, String content) {
        headers.put("Set-Cookie", name + "=" + content);
    }

    public void sendResponse(DataOutputStream dos) {
        try {
            dos.writeBytes(httpStatus + " \r\n");
            dos.writeBytes(headersToString());
            dos.writeBytes("\r\n");
            if (body != null) {
                dos.write(body, 0, body.length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String headersToString() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> {
            sb.append(key + ": " + headers.get(key) + " \r\n");
        });
        return sb.toString();
    }

}
