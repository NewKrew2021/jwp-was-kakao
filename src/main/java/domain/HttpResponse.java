package domain;

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

    public void forward(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(addBasePath(path));
            httpBody = new HttpBody(body);

            httpStatus = HttpStatus.OK;
            setContentType(path);
            httpHeader.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(httpBody.getBytesSize()));
            httpHeader.addHeader(HEADER_CONTENT_LOCATION, path);
            send();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            sendInternalServerError();
        }
    }

    private String addBasePath(String path) {
        if (path.endsWith(EXTENSION_HTML) || path.endsWith(EXTENSION_ICO)) {
            return TEMPLATE_BASE_PATH + path;
        }
        return STATIC_BASE_PATH + path;
    }

    private void setContentType(String path) {
        if (path.endsWith(EXTENSION_CSS)) {
            httpHeader.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_CSS);
        }
        if(path.endsWith(EXTENSION_HTML)) {
            httpHeader.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_HTML);
        }
    }

    public void forwardBody(String path, String content) {
        try {
            httpStatus = HttpStatus.OK;
            httpBody = new HttpBody(content);
            setContentType(path);
            httpHeader.addHeader(HEADER_CONTENT_LENGTH, String.valueOf(httpBody.getBytesSize()));
            httpHeader.addHeader(HEADER_CONTENT_LOCATION, path);
            send();
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void sendRedirect(String location) {
        try {
            httpStatus = HttpStatus.FOUND;
            httpHeader.addHeader(HEADER_LOCATION, location);
            send();
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void sendInternalServerError() {
        try {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            dos.writeBytes(this.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCookieWithPath(String key, String value, String path) {
        httpHeader.addHeader(HEADER_SET_COOKIE, String.format(SET_COOKIE_VALUE_FORMAT, key, value, path));
    }

    private void send() throws IOException {
        dos.writeBytes(HTTP_VERSION + " " + httpStatus + " \r\n");
        dos.writeBytes(httpHeader.toString());
        if(httpBody != null) {
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