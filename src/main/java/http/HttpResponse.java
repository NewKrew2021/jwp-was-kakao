package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final static String HTTP_VERSION = "HTTP/1.1";
    private final static String LOCATION = "Location";
    private final static String SET_COOKIE = "Set-Cookie";
    private final static String PATH = "; Path=/";
    private final static String CONTENT_TYPE = "Content-Type";
    private final static String CONTENT_LENGTH = "Content-Length";
    private final static String ACCEPT = "Accept";
    private final static String HOST = "Host";
    private final static String ENCODING = "text/html;charset=utf-8";
    private final static String HTTP = "Http://";

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private HttpHeader httpHeader;
    private String login;

    public HttpResponse(OutputStream out, HttpHeader httpHeader) {
        dos = new DataOutputStream(out);
        this.httpHeader = httpHeader;
        login = this.httpHeader.getHeaderByKey(SET_COOKIE);
    }

    public void login() {
        login = "logined=true";
    }

    public void sendRedirect(String url) {
        try {
            dos.writeBytes( HTTP_VERSION + " "  + HttpStatus.FOUND.toString() +"\r\n");
            dos.writeBytes( LOCATION + ": " + HTTP + httpHeader.getHeaderByKey(HOST) + url + "\r\n");
            dos.writeBytes( SET_COOKIE + ": " + login + PATH + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardStatic(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes( HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( CONTENT_TYPE + ": "  + httpHeader.getHeaderByKey(ACCEPT) + "\r\n");
            dos.writeBytes( CONTENT_LENGTH + ": "  + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardTemplate(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes( HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( CONTENT_TYPE + ": "  + ENCODING + "\r\n");
            dos.writeBytes( CONTENT_LENGTH + ": "  + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void response200Header() {
        this.response200Header(0);
    }

    public void response200Header(int bodyLength) {
        try {
            dos.writeBytes( HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( CONTENT_TYPE + ": "  + ENCODING + "\r\n");
            dos.writeBytes( CONTENT_LENGTH + ": "  + bodyLength + "\r\n");
            dos.writeBytes( SET_COOKIE + ": " + login + PATH + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


    public void response404() {
        try {
            dos.writeBytes( HTTP_VERSION + " "  + HttpStatus.NOT_FOUND.toString() +"\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
