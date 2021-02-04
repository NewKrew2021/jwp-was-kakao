package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface ResponseStatus {

    Logger logger = LoggerFactory.getLogger(ResponseStatus.class);

    default void setStatus(DataOutputStream dos) throws IOException {
        writeStatus(dos);
    }

    void writeStatus(DataOutputStream dos) throws IOException;
}
