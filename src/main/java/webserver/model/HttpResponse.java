package webserver.model;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String INDEX = "/index.html";
    private static final String TEMPLATE_BASE_PATH = "./templates";
    private static final String STATIC_BASE_PATH = "./static";

    private DataOutputStream dos;

    private HttpMethod method;
    private String path;
    private HttpStatus status = HttpStatus.NOT_FOUND;
    private HttpHeader header = new HttpHeader();
    private HttpBody body = new HttpBody();
    private Parameter parameter;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void forward(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(addBasePath(path));

            send200(dos);
            sendHeader(dos);
            sendNewLine(dos);
            sendBody(dos);
//            writeResponseBody(body);

        } catch (IOException e) {
            sendInternalServerError();
        } catch (URISyntaxException e) {
            sendInternalServerError();
        }
    }


    public void sendRedirect() {
        try {
            send302(dos);
            sendHeader(dos);
            sendNewLine(dos);
        } catch (IOException e) {
            sendInternalServerError();
        }
    }

    private void send200(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
    }

    private void send302(DataOutputStream dos) throws IOException {
        dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
    }

    private void sendNewLine(DataOutputStream dos) throws IOException {
        dos.writeBytes("\r\n");
    }

    private void sendHeader(DataOutputStream dos) throws IOException {
        dos.writeBytes(header.toString());
    }

    private void sendBody(DataOutputStream dos) throws IOException {
        dos.write(body.toString().getBytes(), 0, body.toString().length());
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
        return "text/html";
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

    public void addHeader(String key, String value) {
        header.add(key, value);
    }

    public void addCookie(String key, String value) {
        header.addCookie(key, value);
    }

    public void addBody(String newBody) {
        body.add(newBody);
    }

    public HttpBody getBody() {
        return body;
    }

    public String getHeader(String key) {
        return header.getHeader(key);
    }

    public String getCookie(String key) {
        return header.getCookie(key);
    }

//    public String toString() {
//        String startString = HTTP_VERSION + status.getMessage() +
//                String headerString = header.toString();
//        return
//    }
}
