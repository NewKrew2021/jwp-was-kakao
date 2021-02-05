package webserver.domain;

import com.google.common.net.HttpHeaders;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final String DEFAULT_LOGIN_COOKIE = "logined=false";

    private DataOutputStream dos;
    private Map<String, String> responseHeader;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        responseHeader = new HashMap<>();
    }

    public void response302Found(String url) throws IOException {
        dos.writeBytes(HttpStatus.FOUND_302.getHeader());
        dos.writeBytes(HttpHeaders.LOCATION + ": " + url + " \r\n");
        dos.writeBytes(HttpHeaders.SET_COOKIE + ": " + responseHeader.getOrDefault(HttpHeaders.SET_COOKIE, DEFAULT_LOGIN_COOKIE) + "; Path=/\r\n");
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void response200Ok(byte[] body, String contentType) throws IOException {
        dos.writeBytes(HttpStatus.OK_200.getHeader());
        dos.writeBytes(HttpHeaders.CONTENT_TYPE + ": " + contentType + "\r\n");
        dos.writeBytes(HttpHeaders.CONTENT_LENGTH + ": " + body.length + "\r\n");
        dos.writeBytes("\r\n");
        responseBody(body);
    }

    public void response404() throws IOException {
        dos.writeBytes(HttpStatus.NOT_FOUND_404.getHeader());
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void responseBody(byte[] body) throws IOException {
        dos.write(body, 0, body.length);
        dos.flush();
    }

    public void addHeader(String key, String value) {
        responseHeader.put(key, value);
    }

}
