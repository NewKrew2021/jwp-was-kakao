package controller;

import annotation.web.RequestMethod;
import request.HttpRequest;
import response.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals(RequestMethod.POST)) {
            doPost(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.getMethod().equals(RequestMethod.GET)) {
            doGet(httpRequest, httpResponse);
            return;
        }
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

}
