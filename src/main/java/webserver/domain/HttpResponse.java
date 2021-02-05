package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.TemplateUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.*;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatusCode httpStatusCode;
    private Map<String, String> headers;
    private Cookies cookies;
    private byte[] body;

    public HttpResponse(HttpStatusCode httpStatusCode, Map<String, String> headers, Cookies cookies, byte[] body) {
        this.httpStatusCode = httpStatusCode;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
    }

    public void sendResponse(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes("HTTP/1.1 " + httpStatusCode.getPhrase() + " \r\n");
        dos.writeBytes(headersList());
        dos.writeBytes("\r\n");
        if (this.body != null) {
            dos.write(this.body, 0, this.body.length);
        }
        dos.flush();
    }

    private String headersList() {
        StringBuilder sb = new StringBuilder();
        headers.keySet().forEach(key -> sb.append(key)
                .append(": ")
                .append(headers.get(key))
                .append("\r\n"));
        sb.append(cookies.toString());

        return sb.toString();
    }

    public static class Builder {
        private HttpStatusCode httpStatusCode;
        private Map<String, String> headers = new HashMap<>();
        private Cookies cookies = new Cookies();
        private byte[] body;

        public Builder status(HttpStatusCode httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder body(String path) throws IOException, URISyntaxException {
            this.body = FileIoUtils.loadFileFromClasspath(path);
            headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length));

            return this;
        }

        public Builder body(String path, Map<String, Object> parameter) throws IOException {
            this.body = TemplateUtils.getTemplatePage(path, parameter);
            logger.debug(new String(body));
            headers.put(HttpHeader.CONTENT_LENGTH, String.valueOf(this.body.length));
            return this;
        }

        public Builder cookie(Cookie cookie) {
            this.cookies.addCookie(cookie);
            return this;
        }

        public Builder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder contentType(String value) {
            this.headers.put(HttpHeader.CONTENT_TYPE, value);
            return this;
        }

        public Builder redirect(String path) {
            this.headers.put(HttpHeader.LOCATION, path);
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(httpStatusCode, headers, cookies, body);
        }
    }
}
