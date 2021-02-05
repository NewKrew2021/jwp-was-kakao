package framework.response;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response404Status implements ResponseStatus {

    private final String MESSAGE = "HTTP/1.1 404 Not Found \r\n";

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException{
        dos.writeBytes(MESSAGE);
    }
}
