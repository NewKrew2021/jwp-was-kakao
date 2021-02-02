package model;

import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private final DataOutputStream dos;
    private final Map<String, String> headers = new HashMap<>();

    private int status;
    private String startLine;
    private byte[] body;

    private static final String PROTOCOL = "HTTP/1.1";
    private static final Map<String, String> contentType = new HashMap<>();
    static {
        contentType.put(".js", "text/js");
        contentType.put(".html", "text/html");
        contentType.put(".css", "text/css");
    }

    private static final Map<Integer, String> httpStatusCode = new HashMap<>();
    static {
        httpStatusCode.put(200, "OK");
        httpStatusCode.put(302, "FOUND");
        httpStatusCode.put(404, "NOT FOUND");
        httpStatusCode.put(500, "INTERNAL SERVER ERROR");
    }

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(OutputStream os) {
        return new HttpResponse(new DataOutputStream(os));
    }

    public HttpResponse setStatus(int statusCode) {
        if (httpStatusCode.get(statusCode) == null)
            throw new RuntimeException("상태코드가 유효하지 않습니다.");

        status = statusCode;
        startLine = String.join(" ", PROTOCOL,
                String.valueOf(statusCode), httpStatusCode.get(statusCode));

        return this;
    }

    public HttpResponse setHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpResponse setCookie(String cookie) {
        headers.put("Set-Cookie", cookie);
        return this;
    }

    public HttpResponse setLocation(String url) {
        if (status != 201 && status / 100 != 3)
            throw new RuntimeException("올바르지 않은 요청입니다");

        headers.put("Location", url);
        return this;
    }

    public void sendFile(String basePath, String path) throws URISyntaxException, IOException {
        body = FileIoUtils.loadFileFromClasspath(basePath + path);

        String extension = path.substring(path.lastIndexOf("."));
        if (contentType.get(extension) == null)
            throw new RuntimeException("확장자가 유효하지 않습니다.");
        setHeader("Content-Type", contentType.get(extension) + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
    }

    public void sendHtml(byte[] body) throws IOException {
        this.body = body;
        setHeader("Content-Type", contentType.get("html") + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
    }

    public void ok() throws IOException {
        dos.writeBytes(startLine);
        dos.writeBytes("\r\n");

        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");

        if (body != null && body.length != 0)
            dos.write(body, 0, body.length);

        dos.flush();
    }

    public void sendView(byte[] body) throws IOException {
        setStatus(200);
        sendHtml(body);
        ok();
    }

    public void sendRedirect(String url) throws IOException {
        setStatus(302);
        setLocation(url);
        ok();
    }

    public void forward(String basePath, String path) throws URISyntaxException, IOException {
        setStatus(200);
        sendFile(basePath, path);
        ok();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getStartLine() {
        return startLine;
    }
}
