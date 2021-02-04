package controller;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod() == HttpMethod.POST) {
            doPost(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.getMethod() == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }
        httpResponse.badRequest();
    }

    public abstract boolean match(String path);

    public abstract String getPath();

    public abstract void doPost(HttpRequest httpRequest, HttpResponse httpResponse);

    public abstract void doGet(HttpRequest httpRequest, HttpResponse httpResponse);
}
