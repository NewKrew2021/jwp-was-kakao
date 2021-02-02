package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseUnauthorized extends Response {

    private ResponseUnauthorized() {
    }

    public static ResponseUnauthorized create() {
        return new ResponseUnauthorized();
    }

    @Override
    public void write(DataOutputStream dos) {
        response401Header(dos);
    }

    private void response401Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 401 Unauthorized \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
