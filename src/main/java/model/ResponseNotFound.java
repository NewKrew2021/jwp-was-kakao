package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseNotFound extends Response {

    private ResponseNotFound() {
    }

    public static ResponseNotFound create() {
        return new ResponseNotFound();
    }

    @Override
    public void write(DataOutputStream dos) {
        response404Header(dos);
    }

    private void response404Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
