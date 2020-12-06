package webserver.http;

public interface Controller {
    ModelAndView execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
