package http.response;


import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private OutputStream out;
    private String status;
    private final Map<String, String> headers = new HashMap<>();
    private byte[] body = new byte[0];

    public HttpResponse(byte[] body) {
        setBody(body, "");
    }

    public HttpResponse(String status, byte[] body) {
        this(body);
        this.status = status;
    }

    public HttpResponse(OutputStream out) {
        this.out = out;
    }

    public void setBody(byte[] body, String contentType) {
        this.body = body;
        this.headers.put("Content-Type", contentType + ";charset=utf-8");
        this.headers.put("Content-Length", "" + body.length);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) {
        try {
            if (path.endsWith(".html")) {
                forwardBody(FileIoUtils.loadFileFromClasspath("templates/" + path), "text/html");
            }

            if (path.endsWith(".css")) {
                forwardBody(FileIoUtils.loadFileFromClasspath("static/" + path), "text/css");
            }

            if (path.endsWith(".js")) {
                forwardBody(FileIoUtils.loadFileFromClasspath("static/" + path), "text/javascript");
            }
        } catch (Exception e) {
            internalServerError();
        }
    }

    public void forwardBody(byte[] body, String contentType) {
        setBody(body, contentType);
        ok();
    }

    public void ok() {
        this.status = "HTTP/1.1 200 ok";

        sendHttpResponse();
    }

    public void internalServerError() {
        this.status = "HTTP/1.1 500 INTERNAL SERVER ERROR";

        sendHttpResponse();
    }

    public void notFound() {
        this.status = "HTTP/1.1 404 NOT FOUND";

        sendHttpResponse();
    }

    public void badRequest() {
        this.status = "HTTP/1.1 400 BAD REQUEST";

        sendHttpResponse();
    }

    public void sendRedirect(String path) {
        this.status = "HTTP/1.1 302 Found";
        addHeader("Location", "http://localhost:8080" + path);
        sendHttpResponse();
    }

    public String getHeaders() {
        StringBuilder message = new StringBuilder();
        message.append(status + "\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            message.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        message.append("\r\n");

        return String.valueOf(message);
    }

    public byte[] getBody() {
        return body;
    }

    private void sendHttpResponse() {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes(getHeaders());
            dos.write(getBody(), 0, getBody().length);
            dos.flush();
        } catch (IOException e) {

        }
    }

    public void setSessionId(String sessionId) {
        headers.put("Set-Cookie", "SessionId=" + sessionId + "; Path=/");
    }
}