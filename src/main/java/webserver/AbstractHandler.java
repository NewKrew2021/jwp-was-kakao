package webserver;

import annotation.web.RequestMethod;
import domain.HttpRequest;
import domain.HttpResponse;

public abstract class AbstractHandler implements Handler {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }
        doPost(httpRequest, httpResponse);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }
}
