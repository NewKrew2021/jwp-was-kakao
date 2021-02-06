package framework.controller;

import framework.request.HttpRequest;
import framework.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    void service(HttpRequest request, HttpResponse response) throws Exception;
}
