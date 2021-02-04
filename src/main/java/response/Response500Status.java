package response;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response500Status implements ResponseStatus {

    private final String MESSAGE = "HTTP/1.1 500 Internal Server Error \r\n";

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes(MESSAGE);
    }
}
