package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseOK extends Response {
    private final String contentType;
    private final byte[] body;

    private ResponseOK(String contentType, byte[] body) {
        this.contentType = contentType;
        this.body = body;
    }

    public static ResponseOK of(String contentType, byte[] body) {
        return new ResponseOK(contentType, body);
    }

    @Override
    public void write(DataOutputStream dos) {
        response200Header(dos);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
