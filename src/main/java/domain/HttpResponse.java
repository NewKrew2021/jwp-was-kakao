package domain;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {

    private final String INDEX = "/index.html";
    private final String TEMPLATE_BASE_PATH = "./templates";
    private final String STATIC_BASE_PATH = "./static";

    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String path) {
        if(path.equals("/")) {
            path = INDEX;
        }

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(addBasePath(path));
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + findContentType(path) + "; charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("Content-Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
            writeResponseBody(body);
        } catch (IOException e) {
            sendInternalServerError();
        } catch (URISyntaxException e) {
            sendInternalServerError();
        }
    }

    private String addBasePath(String path) {
        if (path.startsWith("/css")
                || path.startsWith("/fonts")
                || path.startsWith("/images")
                || path.startsWith("/js")) {
            return STATIC_BASE_PATH + path;
        }
        return TEMPLATE_BASE_PATH + path;
    }

    private String findContentType(String path) {
        if (path.startsWith("/css")) {
            return "text/css";
        }
//        if (path.startsWith("/fonts")
//                || path.startsWith("/images")
//                || path.startsWith("/js")) {
//
//        }
        return "text/html";
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void loginTrue() {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + INDEX + "\r\n");
            dos.writeBytes("Set-Cookie: logined=true; Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void loginFalse() {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: /user/login_failed.html\r\n");
            dos.writeBytes("Set-Cookie: logined=false; Path=/\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    private void writeResponseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    public void sendInternalServerError() {
        try {
            dos.writeBytes("HTTP/1.1 500 Internal Server Error\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUserList(String content) {
        byte[] body = content.getBytes();
        String path = "/user/list";
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + findContentType(path) + "; charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("Content-Location: " + path + "\r\n");
            dos.writeBytes("\r\n");
            writeResponseBody(body);
        } catch (IOException e) {
            sendInternalServerError();
        }
    }
}
