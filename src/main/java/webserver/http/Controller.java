package webserver.http;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
