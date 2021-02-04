package model;

import exception.http.IllegalExtensionException;
import exception.http.IllegalHttpRequestException;
import exception.http.IllegalLocationException;
import exception.http.IllegalStatusCodeException;
import exception.utils.NoFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String PROTOCOL = "HTTP/1.1";

    private final DataOutputStream dos;
    private final Map<String, String> headers = new HashMap<>();

    private int status;
    private String startLine;
    private byte[] body;

    private static final Map<String, String> contentType = new HashMap<>();

    static {
        contentType.put(".js", "text/js");
        contentType.put(".html", "text/html");
        contentType.put(".css", "text/css");
        contentType.put(".woff", "application/font-woff");
        contentType.put(".ttf", "application/x-font-ttf");
        contentType.put(".ico", "image/x-icon");
    }

    private static final Map<Integer, String> httpStatusCode = new HashMap<>();
    static {
        httpStatusCode.put(200, "OK");
        httpStatusCode.put(201, "CREATED");
        httpStatusCode.put(302, "FOUND");
        httpStatusCode.put(400, "BAD REQUEST");
        httpStatusCode.put(401, "Unauthorized");
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
            throw new IllegalStatusCodeException();

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
        headers.put("Set-Cookie", cookie + "; Path=/; HttpOnly");
        return this;
    }

    public HttpResponse setLocation(String url) {
        if (status != 201 && status / 100 != 3)
            throw new IllegalLocationException();

        headers.put("Location", url);
        return this;
    }

    public HttpResponse sendFile(String basePath, String path) throws NoFileException {
        try {
            body = FileIoUtils.loadFileFromClasspath(basePath + path);
        }catch (NoFileException e){
            throw new NoFileException(path);
        }

        String extension = path.substring(path.lastIndexOf("."));
        if (contentType.get(extension) == null)
            throw new IllegalExtensionException();
        setHeader("Content-Type", contentType.get(extension) + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));

        return this;
    }

    public void sendHtml(byte[] body) {
        this.body = body;
        setHeader("Content-Type", contentType.get("html") + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
    }

    public void ok() {
        try {
            dos.writeBytes(startLine);
            dos.writeBytes("\r\n");

            for (Map.Entry<String, String> header : headers.entrySet()) {
                dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
            }
            dos.writeBytes("\r\n");

            if (body != null && body.length != 0)
                dos.write(body, 0, body.length);

            dos.flush();
            log.info("{}", startLine);
        }catch (IOException e){
            throw new IllegalHttpRequestException();
        }
    }

    public void sendView(byte[] body) {
        setStatus(200);
        sendHtml(body);
        ok();
    }

    public void sendRedirect(String url) {
        setStatus(302);
        setLocation(url);
        ok();
    }

    public void forward(String basePath, String path) throws NoFileException {
        try{
            setStatus(200);
            sendFile(basePath, path);
            ok();
        }catch (NoFileException e){
            throw new NoFileException(path);
        }

    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getStartLine() {
        return startLine;
    }
}
