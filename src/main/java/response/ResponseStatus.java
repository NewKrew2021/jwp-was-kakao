package response;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ResponseStatus {

    String MESSAGE = "";

    default void setStatus(DataOutputStream dos) throws IOException {
        writeStatus(dos);
    }

    void writeStatus(DataOutputStream dos) throws IOException;
}
