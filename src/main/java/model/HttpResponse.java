package model;

import exception.http.IllegalLocationException;
import exception.utils.NoFileException;
import model.httpinfo.ContentType;
import model.httpinfo.HttpStatusMessage;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String PROTOCOL = "HTTP/1.1";

    private final Map<String, String> headers = new HashMap<>();

    private int status;
    private String startLine;
    private byte[] body;

    public HttpResponse setStatus(int statusCode) {
        status = statusCode;
        startLine = String.join(" ", PROTOCOL,
                String.valueOf(statusCode), HttpStatusMessage.of(statusCode));
        return this;
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse setCookie(String cookie) {
        headers.put("Set-Cookie", cookie + "; Path=/; HttpOnly");
        return this;
    }

    public HttpResponse setBody(String body) {
        this.body = body.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public HttpResponse setLocation(String url) {
        if (status != 201 && status / 100 != 3)
            throw new IllegalLocationException();

        headers.put("Location", url);
        return this;
    }

    public HttpResponse sendFile(String basePath, String path) throws NoFileException {
        body = FileIoUtils.loadFileFromClasspath(basePath + path);

        String extension = path.substring(path.lastIndexOf("."));
        setHeader("Content-Type", ContentType.of(extension) + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
        return this;
    }

    public HttpResponse sendHtml(byte[] body) {
        this.body = body;
        setHeader("Content-Type", ContentType.of(".html") + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
        return this;
    }

    public HttpResponse view(byte[] body) {
        setStatus(200);
        sendHtml(body);
        return this;
    }

    public HttpResponse redirect(String url) {
        setStatus(302);
        setLocation(url);
        return this;
    }

    public HttpResponse forward(String basePath, String path) throws NoFileException {
        setStatus(200);
        sendFile(basePath, path);
        return this;
    }

    public void ok(DataOutputStream dos) throws IOException {
        writeStartLine(dos);
        writeHeader(dos);
        writeBody(dos);
        dos.flush();
    }

    private void writeStartLine(DataOutputStream dos) throws IOException {
        dos.writeBytes(startLine);
        dos.writeBytes("\r\n");
    }

    private void writeHeader(DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
    }

    private void writeBody(DataOutputStream dos) throws IOException {
        if (body != null && body.length != 0)
            dos.write(body, 0, body.length);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getStartLine() {
        return startLine;
    }

    public byte[] getBody(){ return body; }
}
