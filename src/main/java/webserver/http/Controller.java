package webserver.http;

public interface Controller {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
