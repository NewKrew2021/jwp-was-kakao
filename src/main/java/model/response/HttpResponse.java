package model.response;

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
import java.util.Map;

public class HttpResponse {
    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static final String PROTOCOL = "HTTP/1.1";

    private final DataOutputStream dos;

    private int status;
    private String startLine;
    private byte[] body;

    private final HttpHeader httpHeader = new HttpHeader();
    private final ContentType contentType = new ContentType();
    private final HttpStatus httpStatus = new HttpStatus();

    private HttpResponse(DataOutputStream dos) {
        this.dos = dos;
    }

    public static HttpResponse of(OutputStream os) {
        return new HttpResponse(new DataOutputStream(os));
    }

    public HttpResponse setStatus(int statusCode) {
        if (httpStatus.get(statusCode) == null)
            throw new IllegalStatusCodeException();

        status = statusCode;
        startLine = String.join(" ", PROTOCOL,
                String.valueOf(statusCode), httpStatus.get(statusCode));

        return this;
    }

    public HttpResponse setHeader(String key, String value) {
        httpHeader.put(key, value);
        return this;
    }

    public HttpResponse setCookie(String cookie) {
        httpHeader.put("Set-Cookie", cookie + "; Path=/; HttpOnly");
        return this;
    }

    public HttpResponse setLocation(String url) {
        if (status != 201 && status / 100 != 3)
            throw new IllegalLocationException();

        httpHeader.put("Location", url);
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

            for (Map.Entry<String, String> header : httpHeader.entrySet()) {
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
        return httpHeader.getHeader();
    }

    public String getStartLine() {
        return startLine;
    }
}
