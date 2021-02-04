package http;

import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import utils.TemplateUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String httpStatus;
    private Map<String, String> headers;
    private byte[] body;
    private Cookies cookies;

    public HttpResponse(String httpStatus, Map<String, String> headers, byte[] body, Cookies cookies) {
        this.httpStatus = httpStatus;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
    }

    public void sendResponse(DataOutputStream dos) throws IOException {
        dos.writeBytes(getHttpStatus() + " \r\n");
        dos.writeBytes(headersToString());
        dos.writeBytes("\r\n");
        if (getBody() != null) {
            dos.write(getBody(), 0, getBody().length);
        }
        dos.flush();
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String headersToString() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(key)
                .append(": ")
                .append(headers.get(key))
                .append("\r\n"));
        sb.append(cookies.toSetCookies());
        return sb.toString();
    }

    public byte[] getBody() {
        return body;
    }

    public static class Builder {
        public static final String HTTP_VERSION = "HTTP/1.1";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String LOCATION = "Location";
        private String httpStatus;
        private Map<String, String> headers = new HashMap<>();
        private byte[] body;
        private Cookies cookies = new Cookies();

        public Builder status(HttpStatus httpStatus) {
            this.httpStatus = String.format("%s %s", HTTP_VERSION, httpStatus.toString());
            return this;
        }

        public Builder contentType(String contentType) {
            headers.put(CONTENT_TYPE, contentType);
            return this;
        }

        public Builder contentLength(int length) {
            headers.put(CONTENT_LENGTH, String.valueOf(length));
            return this;
        }

        public Builder body(String path) throws IOException, URISyntaxException {
            this.body = FileIoUtils.loadFileFromClasspath(path);
            return contentLength(body.length);
        }

        public Builder body(String path, Map<String, Object> params) throws IOException, URISyntaxException {
            this.body = TemplateUtils.getInstance().buildPage(path, params);
            return contentLength(body.length);
        }

        public Builder redirect(String location) {
            headers.put(LOCATION, location);
            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder cookie(Cookie cookie) {
            cookies.add(cookie);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(httpStatus, headers, body, cookies);
        }
    }
}
