package webserver;

import model.User;
import utils.FileIoUtils;
import utils.ParseUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Response {
    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> contentType = new HashMap<>();

    private Response(OutputStream out) {
        dos = new DataOutputStream(out);
        initializeExtension();
    }

    public static Response of(OutputStream dos) {
        return new Response(dos);
    }

    private void initializeExtension() {
        contentType.put("js", "text/javascript");
        contentType.put("css", "text/css");
        contentType.put("html", "text/html");
        contentType.put("ico", "image/png");
        contentType.put("png", "image/png");
        contentType.put("jpg", "image/jpeg");
        contentType.put("jpeg", "image/jpeg");
        contentType.put("svg", "image/svg+xml");
        contentType.put("eot", "application/vnd.ms-fontobject");
        contentType.put("ttf", "application/x-font-ttf");
        contentType.put("woff", "application/font-woff");
        contentType.put("woff2", "application/font-woff2");
    }

    private String getContentType(String extension) {
        return contentType.getOrDefault(extension, "text/html");
    }

    public void forward(String location) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils
                .loadFileFromClasspath(location);
        addHeader("Content-Type", getContentType(ParseUtils.parseExtension(location)) + ";charset=utf-8");
        addHeader("Content-Length", Integer.toString(body.length));
        writeResponse200(body);
    }

    private void writeResponse200(byte[] body) throws IOException {
        dos.writeBytes("HTTP/1.1 200 OK\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + " : " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(body, 0, body.length);
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void sendRedirect(String location) throws IOException {
        dos.writeBytes("HTTP/1.1 302 Found\r\n");
        addHeader("Location", location);
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.flush();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addCookie(Cookie cookie) {
        headers.put("Set-Cookie", cookie.toString());
    }

    public void userListForward(String location, Collection<User> users) throws IOException {
        byte[] body = FileIoUtils.loadCompiledFileFromClassPath(location, users);
        addHeader("Content-Type", getContentType(ParseUtils.parseExtension(location)) + ";charset=utf-8");
        addHeader("Content-Length", Integer.toString(body.length));
        writeResponse200(body);
    }
}
