package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response {
    private static final Logger log = LoggerFactory.getLogger(Response.class);

    public static void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void response200JsHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/js;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void setCookie(DataOutputStream dos, String cookie){
        try {
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void responseWithoutBody(DataOutputStream dos){
        try {
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }

    public static void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes("\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
