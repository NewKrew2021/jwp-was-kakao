package webserver.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.RequestHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private static final String TEMPLATE_PREFIX = "templates";
    private static final String STATIC_PREFIX = "static";

    private DataOutputStream dos;
    private Map<String, String> headers;

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
        headers = new HashMap<>();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String path) throws IOException, URISyntaxException {
        if(isTemplate(path)){
            byte[] response = readFile(path);
            response200Header(response.length);
            responseBody(response);
        }
        byte[] response = readFile(path);
        responseStaticHeader(response.length);
        responseBody(response);
    }

    private void responseStaticHeader(int contentLength) throws IOException{
        dos.writeBytes("HTTP/1.1 200 OK \r\n");
        dos.writeBytes(String.format("%s: %s\r\n", "Content-Type", "text/css;charset=utf-8"));
        dos.writeBytes(String.format("%s: %s\r\n", "Content-Length", contentLength));
        dos.writeBytes("\r\n");
    }

    private boolean isTemplate(String path) {
        return path.endsWith(".html") || path.endsWith(".ico");
    }

    public void forwardBody(String body) {
        byte[] response = body.getBytes();
        response200Header(response.length);
        responseBody(response);
    }

    public void sendRedirect(String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 REDIRECTED\r\n");
            printHeader();
            dos.writeBytes(HttpHeader.LOCATION + ": " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void send405BadMethod() {
        try {
            dos.writeBytes("HTTP/1.1 405 Method Not Allowed\r\n");
            printHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void send501NotImplemented() {
        try {
            dos.writeBytes("HTTP/1.1 501 Not Implemented\r\n");
            printHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            printHeader();
            dos.writeBytes(HttpHeader.CONTENT_TYPE + ": text/html;charset=utf-8\r\n");
            dos.writeBytes(HttpHeader.CONTENT_LENGTH + ": " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printHeader() throws IOException {
        for (String key : headers.keySet()) {
            dos.writeBytes(String.format("%s: %s\r\n", key, headers.get(key)));
        }
    }

    private byte[] readFile(String path) throws IOException, URISyntaxException {
        if (isTemplate(path)) {
            return FileIoUtils.loadFileFromClasspath(TEMPLATE_PREFIX + path);
        }
        return FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + path);
    }
}
