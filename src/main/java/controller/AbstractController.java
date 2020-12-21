package controller;

import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.isPostMethod()) {
            doPost(request, response);

        } else if (request.isGetMethod()) {
            doGet(request, response);
        }
    }

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);
}
