package controller;

import annotation.web.RequestMethod;
import domain.HttpRequest;
import domain.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }
        doPost(httpRequest, httpResponse);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }
}
