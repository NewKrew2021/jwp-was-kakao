package domain;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public class HttpResponse {

    private final String INDEX = "/index.html";

    private DataOutputStream dos;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String path) {
        if(path.equals("/")) {
            path = INDEX;
        }
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + path);
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
            writeResponseBody(body);
        } catch (IOException e) {
            sendInternalServerError();
        } catch (URISyntaxException e) {
            sendInternalServerError();
        }
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
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + INDEX);
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("Set-Cookie: logined=true; Path=/\r\n");
            dos.writeBytes("\r\n");
            writeResponseBody(body);
        } catch (IOException e) {
            sendInternalServerError();
        } catch (URISyntaxException e) {
            sendInternalServerError();
        }
    }

    public void loginFalse() {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("Set-Cookie: logined=false; Path=/\r\n");
            dos.writeBytes("\r\n");
            writeResponseBody(body);
        } catch (IOException e) {
            sendInternalServerError();
        } catch (URISyntaxException e) {
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
}
