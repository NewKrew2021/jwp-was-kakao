package controller;

import domain.HttpRequest;
import domain.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class AbstractController implements Controller {

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (httpRequest.getMethod().equals("GET")) {
            doGet(httpRequest, httpResponse);
        }
        if (httpRequest.getMethod().equals("POST")) {
            doPost(httpRequest, httpResponse);
        }
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

}
