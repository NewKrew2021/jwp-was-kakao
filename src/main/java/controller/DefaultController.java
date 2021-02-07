package controller;

import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        response.forward(request.getPath());
    }
}
