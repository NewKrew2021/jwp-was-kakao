package response;

import java.io.DataOutputStream;
import java.io.IOException;

public class AbstractResponseStatus implements ResponseStatus {

    public String MESSAGE;

    @Override
    public void writeStatus(DataOutputStream dos) throws IOException {
        dos.writeBytes(MESSAGE);
    }
}
