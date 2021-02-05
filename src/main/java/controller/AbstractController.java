package controller;

import webserver.HttpMethod;
import webserver.Request;
import webserver.Response;

public abstract class AbstractController implements Controller {


    @Override
    public void service(Request request, Response response) throws Exception {
        HttpMethod httpMethod = HttpMethod.of(request.getMethod());
        switch (httpMethod) {
            case GET:
                doGet(request, response);
                break;
            case POST:
                doPost(request, response);
                break;
        }
    }

    public abstract void doPost(Request request, Response response) throws Exception;

    public abstract void doGet(Request request, Response response) throws Exception;
}
