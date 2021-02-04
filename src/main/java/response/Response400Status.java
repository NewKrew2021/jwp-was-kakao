package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response400Status implements ResponseStatus {

    private static final Logger logger = LoggerFactory.getLogger(Response400Status.class);

    @Override
    public void setStatus(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
