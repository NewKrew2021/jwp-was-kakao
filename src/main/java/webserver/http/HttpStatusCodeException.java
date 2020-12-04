package webserver.http;

abstract public class HttpStatusCodeException extends RuntimeException{

    public HttpStatusCodeException(String message) {
        super(message);
    }

    abstract public HttpStatus getStatus();

}
