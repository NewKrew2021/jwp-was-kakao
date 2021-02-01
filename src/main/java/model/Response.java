package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Response {
    protected static final Logger logger = LoggerFactory.getLogger(Response.class);

    public abstract void write(DataOutputStream dos);

    protected void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
