package controller;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractController implements Controller {

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.getMethod() == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }
        doPost(httpRequest, httpResponse);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

}
