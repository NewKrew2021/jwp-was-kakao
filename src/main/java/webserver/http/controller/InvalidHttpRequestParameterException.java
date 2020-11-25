package webserver.http.controller;

public class InvalidHttpRequestParameterException extends RuntimeException{
    public InvalidHttpRequestParameterException(String message) {
        super(message);
    }
}
