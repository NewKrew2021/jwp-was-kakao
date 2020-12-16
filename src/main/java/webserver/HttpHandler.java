package webserver;

@FunctionalInterface
public interface HttpHandler {

    HttpResponse handle(String method, String target, HttpRequest req) throws Exception;

}
