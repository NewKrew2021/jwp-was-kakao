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

    private String httpStatus;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(String httpStatus, Map<String, String> headers, byte[] body) {
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
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

    public static class Builder {
        private String httpStatus;
        private Map<String, String> headers = new HashMap<>();
        private byte[] body;

        public Builder status(String httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder body(byte[] body, String mimeType) throws IOException, URISyntaxException {
            this.body = body;
            headers.put("Content-Type", mimeType);
            headers.put("Content-Length", String.valueOf(body.length));
            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder redirect(String location) {
            headers.put("Location", location);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(httpStatus, headers, body);
        }
    }
}
