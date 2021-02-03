package domain;

import org.springframework.http.HttpStatus;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final String HTTP_VERSION = "HTTP/1.1";
    private final String INDEX = "/index.html";
    private final String TEMPLATE_BASE_PATH = "./templates";
    private final String STATIC_BASE_PATH = "./static";

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
            httpHeader.addHeader("Content-Type", findContentType(path) + "; charset=utf-8");
            httpHeader.addHeader("Content-Length", String.valueOf(httpBody.getBytesSize()));
            httpHeader.addHeader("Content-Location", path);
            send();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            sendInternalServerError();
        }
    }

    private String addBasePath(String path) {
        if (path.endsWith(".html") || path.endsWith(".ico")) {
            return TEMPLATE_BASE_PATH + path;
        }
        return STATIC_BASE_PATH + path;
    }

    // TODO js, font, ico
    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }
        return "text/html";
    }

    public void sendRedirect(String location) {
        try {
            httpStatus = HttpStatus.FOUND;
            httpHeader.addHeader("Location", location);
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

    public void handleUserList(String content) {
        String path = "/user/list";
        try {
            httpStatus = HttpStatus.OK;
            httpBody = new HttpBody(content);
            httpHeader.addHeader("Content-Type", findContentType(path) + "; charset=utf-8");
            httpHeader.addHeader("Content-Length", String.valueOf(httpBody.getBytesSize()));
            httpHeader.addHeader("Content-Location", path);
            send();
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void addHeader(String key, String value) {
        httpHeader.addHeader(key, value);
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
