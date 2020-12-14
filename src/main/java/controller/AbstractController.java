package controller;

import webserver.Request;
import webserver.Response;

public class AbstractController implements Controller {
    @Override
    public void service(Request request, Response response) {
        if (request.isPostMethod()) {
            doPost(request, response);

        } else if (request.isGetMethod()) {
            doGet(request, response);
        }
    }

    public void doGet(Request request, Response response) {
        // Do nothing
    }

    public void doPost(Request request, Response response) {
        // Do nothing
    }
}
