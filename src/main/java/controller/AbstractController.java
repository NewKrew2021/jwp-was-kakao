package controller;

import utils.FileIoUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    abstract public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException;

    public void doPost() {
    }

    public void doGet() {

    }

}
