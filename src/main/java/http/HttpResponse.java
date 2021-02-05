package http;

import model.Login;
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

    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);
    private DataOutputStream dos;
    private HttpHeader httpHeader;

    public HttpResponse(OutputStream out, HttpHeader httpHeader) {
        dos = new DataOutputStream(out);
        this.httpHeader = httpHeader;
    }

    public void sendRedirect(String url) {
        try {
            dos.writeBytes( HttpHeader.HTTP_VERSION + " "  + HttpStatus.FOUND.toString() +"\r\n");
            dos.writeBytes( HttpHeader.LOCATION + ": " + HttpHeader.HTTP + httpHeader.getHeaderByKey(HttpHeader.HOST) + url + "\r\n");
            dos.writeBytes( HttpHeader.SET_COOKIE + ": " + httpHeader.getLogin().isLogin() + HttpHeader.PATH + "\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardStatic(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes( HttpHeader.HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( HttpHeader.CONTENT_TYPE + ": "  + httpHeader.getHeaderByKey(HttpHeader.ACCEPT) + "\r\n");
            dos.writeBytes( HttpHeader.CONTENT_LENGTH + ": "  + body.length + "\r\n");
            dos.writeBytes("\r\n");
            responseBody(body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    public void forwardTemplate(String path) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(path);
            dos.writeBytes( HttpHeader.HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( HttpHeader.CONTENT_TYPE + ": "  + HttpHeader.ENCODING + "\r\n");
            dos.writeBytes( HttpHeader.CONTENT_LENGTH + ": "  + body.length + "\r\n");
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
            dos.writeBytes( HttpHeader.HTTP_VERSION + " "  + HttpStatus.OK.toString() +"\r\n");
            dos.writeBytes( HttpHeader.CONTENT_TYPE + ": "  + HttpHeader.ENCODING + "\r\n");
            dos.writeBytes( HttpHeader.CONTENT_LENGTH + ": "  + bodyLength + "\r\n");
            dos.writeBytes( HttpHeader.SET_COOKIE + ": " + httpHeader.getLogin().isLogin() + HttpHeader.PATH + "\r\n");
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
            dos.writeBytes( HttpHeader.HTTP_VERSION + " "  + HttpStatus.NOT_FOUND.toString() +"\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public Login getLogin() {
        return httpHeader.getLogin();
    }
}
