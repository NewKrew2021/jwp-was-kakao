package controller;

import annotation.web.RequestMethod;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.NoSuchFileException;
import exception.UnsupportedMethodException;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, UnsupportedMethodException, NoSuchFileException {
        if (httpRequest.getMethod() == RequestMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }
        if(httpRequest.getMethod() == RequestMethod.POST) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new UnsupportedMethodException();
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, NoSuchFileException {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException, NoSuchFileException {
    }
}
