package domain;

import exception.*;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import static domain.HttpHeader.*;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1";

    private static final String TEMPLATE_BASE_PATH = "./templates";
    private static final String STATIC_BASE_PATH = "./static";

    private static final String CONTENT_TYPE_CSS = "text/css; charset=utf-8";
    private static final String CONTENT_TYPE_HTML = "text/html; charset=utf-8";

    private static final String SET_COOKIE_VALUE_FORMAT = "%s=%s; Path=%s";

    private HttpHeader httpHeader;
    private HttpStatus httpStatus;
    private HttpBody httpBody;

    public static class Builder {
        private final HttpStatus httpStatus;
        private final HttpHeader httpHeader;
        private HttpBody httpBody;

        public Builder(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            httpHeader = new HttpHeader();
            httpHeader.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_HTML);
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

    public void send(OutputStream out) throws HttpException {
        DataOutputStream dos = new DataOutputStream(out);
        try {
            dos.writeBytes(HTTP_VERSION + " " + httpStatus + " \r\n");
            dos.writeBytes(httpHeader.toString());
            if (httpBody != null) {
                dos.write(httpBody.getBytes(), 0, httpBody.getBytesSize());
                dos.flush();
            }
        } catch(IOException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR);
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
