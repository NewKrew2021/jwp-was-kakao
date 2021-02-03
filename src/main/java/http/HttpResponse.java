package http;

import utils.FileIoUtils;
import utils.TemplateUtils;

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

    public String getHttpStatus() {
        return httpStatus;
    }

    public byte[] getBody() {
        return body;
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

    public static class Builder {
        private String httpStatus;
        private Map<String, String> headers = new HashMap<>();
        private byte[] body;
        private Cookies cookies = new Cookies();

        public Builder status(String httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder contentType(String contentType) {
            headers.put("Content-Type", contentType);
            return this;
        }

        public Builder contentLength(int length) {
            headers.put("Content-Length", String.valueOf(length));
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
            headers.put("Location", location);
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
