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

    private int status;
    private String startLine;
    private final Map<String, String> headers = new HashMap<>();
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
                String.valueOf(statusCode), httpStatusCode.get(statusCode)) + "\r\n";

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
        ok();
    }

    public void ok() throws IOException {
        dos.writeBytes(startLine);

        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");

        if (body != null && body.length != 0)
            dos.write(body, 0, body.length);

        dos.flush();
    }

    public void sendView(byte[] body) throws IOException {
        this.body = body;
        setHeader("Content-Type", contentType.get("html") + ";charset=utf-8");
        setHeader("Content-Length", String.valueOf(body.length));
        ok();
    }
//
//    public void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
//        try {
//            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
//            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    public void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
//        try {
//            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
//            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    public void response200JsHeader(DataOutputStream dos, int lengthOfBodyContent) {
//        try {
//            dos.writeBytes("HTTP/1.1 200 OK \r\n");
//            dos.writeBytes("Content-Type: text/js;charset=utf-8\r\n");
//            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    public void setCookie(DataOutputStream dos, String cookie){
//        try {
//            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    public void response302Header(DataOutputStream dos, String url) {
//        try {
//            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
//            dos.writeBytes("Location: " + url + "\r\n");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    public void responseWithoutBody(DataOutputStream dos){
//        try {
//            dos.writeBytes("\r\n");
//            dos.flush();
//        } catch (IOException e){
//            log.error(e.getMessage());
//        }
//    }
//
//    public void responseBody(DataOutputStream dos, byte[] body) {
//        try {
//            dos.writeBytes("\r\n");
//            dos.write(body, 0, body.length);
//            dos.flush();
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
}
