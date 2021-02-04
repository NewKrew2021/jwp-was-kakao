package controller;

import webserver.Request;
import webserver.Response;

public class DefaultController extends AbstractController {
    @Override
    public void doPost(Request request, Response response) throws Exception {
        response.forward(request.getUri());
    }

    @Override
    public void doGet(Request request, Response response) throws Exception {
        response.forward(request.getUri());
    }
}
