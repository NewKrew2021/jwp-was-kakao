package webserver;

import java.io.IOException;

public class RequestReadException extends RuntimeException {
    public RequestReadException(IOException e) {
        super(e);
    }
}
