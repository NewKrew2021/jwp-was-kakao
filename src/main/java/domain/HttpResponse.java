package domain;

import exception.ExceptionHandler;
import exception.FileIOException;
import exception.HttpResponseOutputException;
import exception.NoSuchFileException;
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

    private static final String EXTENSION_CSS = ".css";
    private static final String EXTENSION_ICO = ".ico";
    private static final String EXTENSION_HTML = ".html";

    private static final String SET_COOKIE_VALUE_FORMAT = "%s=%s; Path=%s";

    private DataOutputStream dos;
    private HttpHeader httpHeader;
    private HttpStatus httpStatus;
    private HttpBody httpBody;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        httpHeader = new HttpHeader();
    }

    public HttpResponse body(byte[] body) {
        httpBody = new HttpBody(body);
        httpHeader.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(body.length));
        return this;
    }

    public void send(HttpStatus httpStatus) throws HttpResponseOutputException {
        this.httpStatus = httpStatus;
        httpHeader = new HttpHeader();
        httpBody = null;
        send();
    }

    public HttpResponse ok(String path) {
        httpStatus = HttpStatus.OK;
        setContentType(path);
        return this;
    }

    private void setContentType(String path) {
        if (path.endsWith(EXTENSION_CSS)) {
            httpHeader.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_CSS);
        }
        if(path.endsWith(EXTENSION_HTML)) {
            httpHeader.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_HTML);
        }
    }

    public HttpResponse redirect(String location) {
        httpStatus = HttpStatus.FOUND;
        httpHeader.addHeader(HEADER_LOCATION, location);
        return this;
    }

    public void setCookieWithPath(String key, String value, String path) {
        httpHeader.addHeader(HEADER_SET_COOKIE, String.format(SET_COOKIE_VALUE_FORMAT, key, value, path));
    }

    public void send() throws HttpResponseOutputException {
        try {
            dos.writeBytes(HTTP_VERSION + " " + httpStatus + " \r\n");
            dos.writeBytes(httpHeader.toString());
            if (httpBody != null) {
                dos.write(httpBody.getBytes(), 0, httpBody.getBytesSize());
                dos.flush();
            }
        } catch(IOException e) {
            throw new HttpResponseOutputException();
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
