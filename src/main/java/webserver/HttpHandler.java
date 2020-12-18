package webserver;

@FunctionalInterface
public interface HttpHandler {

    HttpResponse handle(HttpRequest req) throws Exception;

}
