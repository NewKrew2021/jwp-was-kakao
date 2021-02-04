package controller;

import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) throws Exception {
        if (request.getMethod().equals("GET")) {
            doGet(request, response);
        }
        if (request.getMethod().equals("POST")) {
            doPost(request, response);
        }
    }

    public abstract void doPost(Request request, Response response) throws Exception;

    public abstract void doGet(Request request, Response response) throws Exception;
}
