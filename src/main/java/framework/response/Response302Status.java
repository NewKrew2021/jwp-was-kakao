package framework.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response302Status implements ResponseStatus {

    private final String MESSAGE = "HTTP/1.1 302 Found \r\n";

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes(MESSAGE);
    }
}
