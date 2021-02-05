package service.controller;

import framework.request.HttpRequest;
import framework.response.HttpResponse;
import service.controller.AbstractController;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward(request.getPath());
    }
}
