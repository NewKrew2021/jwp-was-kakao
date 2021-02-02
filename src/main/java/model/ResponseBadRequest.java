package model;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseBadRequest extends Response {

    private ResponseBadRequest() {
    }

    public static ResponseBadRequest create() {
        return new ResponseBadRequest();
    }

    @Override
    public void write(DataOutputStream dos) {
        response401Header(dos);
    }

    private void response401Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
