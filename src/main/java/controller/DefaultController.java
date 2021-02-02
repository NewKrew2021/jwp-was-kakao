package controller;

import webserver.Request;
import webserver.Response;

public class DefaultController extends AbstractController {
    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }
        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    @Override
    public void doGet(Request request, Response response) throws Exception {
        response.forward(request.getUri());
    }
}
