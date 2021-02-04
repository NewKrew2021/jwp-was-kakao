package response;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response200Status implements ResponseStatus {

    private final String MESSAGE = "HTTP/1.1 200 OK \r\n";

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes(MESSAGE);
    }
}
