package webserver;

public class HttpStatusMessage {
    public final static String ok = "HTTP/1.1 200 ok";
    public final static String redirect = "HTTP/1.1 302 Found";
    public final static String badRequest = "HTTP/1.1 400 BAD REQUEST";
    public final static String notFount = "HTTP/1.1 404 NOT FOUND";
    public final static String internalServerError = "HTTP/1.1 500 INTERNAL SERVER ERROR";
}
