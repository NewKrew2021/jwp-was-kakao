package domain;

import org.springframework.http.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static domain.HttpHeader.*;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1";

    private static final String SET_COOKIE_VALUE_FORMAT = "%s=%s; Path=%s";

    private final HttpHeader httpHeader;
    private final HttpStatus httpStatus;
    private final HttpBody httpBody;

    public static class Builder {
        private HttpStatus httpStatus;
        private HttpHeader httpHeader;
        private HttpBody httpBody;

        public Builder() {
            httpHeader = new HttpHeader();
        }

        public Builder(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            httpHeader = new HttpHeader();
        }

        public Builder ok(byte[] body) {
            httpStatus = HttpStatus.OK;
            body(body);
            return this;
        }

        public Builder redirect(String path) {
            httpStatus = HttpStatus.FOUND;
            httpHeader.addHeader(HEADER_LOCATION, path);
            return this;
        }

        public Builder addHeader(String key, String value) {
            httpHeader.addHeader(key, value);
            return this;
        }

        public Builder setCookie(String key, String value, String path) {
            httpHeader.addHeader(HEADER_SET_COOKIE, String.format(SET_COOKIE_VALUE_FORMAT, key, value, path));
            return this;
        }

        public Builder body(byte[] body) {
            httpBody = new HttpBody(body);
            httpHeader.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(body.length));
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    private HttpResponse(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.httpHeader = builder.httpHeader;
        this.httpBody = builder.httpBody;
    }

    public void send(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.writeBytes(HTTP_VERSION + " " + httpStatus + " \r\n");
        dos.writeBytes(httpHeader.toString());
        if (httpBody != null) {
            dos.write(httpBody.getBytes(), 0, httpBody.getBytesSize());
            dos.flush();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HTTP_VERSION).append(' ').append(httpStatus).append(" \r\n");
        sb.append(httpHeader);
        if(httpBody != null) {
            sb.append(httpBody.toString());
        }
        return sb.toString();
    }
}
