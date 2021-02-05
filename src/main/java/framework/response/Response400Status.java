package framework.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response400Status implements ResponseStatus {

    private final String MESSAGE = "HTTP/1.1 400 Bad Request \r\n";

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes(MESSAGE);
    }
}
