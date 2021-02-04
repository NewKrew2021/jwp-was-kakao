package controller;

import exception.NotDefinedMethodException;
import webserver.Request;
import webserver.Response;

public class DefaultController extends AbstractController {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        response.forward(getContentLocation(request.getUri()));
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
