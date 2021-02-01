package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseFound extends Response {
    private final String url;

    private ResponseFound(String url) {
        this.url = url;
    }

    public static ResponseFound from(String url) {
        return new ResponseFound(url);
    }

    @Override
    public void write(DataOutputStream dos) {
        response302Header(dos);
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
