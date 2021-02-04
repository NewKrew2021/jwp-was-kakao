package response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class Response200Status implements ResponseStatus {

    private static final Logger logger = LoggerFactory.getLogger(Response200Status.class);

    @Override
    public void setStatus(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
