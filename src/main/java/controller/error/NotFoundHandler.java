package controller.error;

import controller.handler.ErrorHandler;
import model.HttpRequest;
import model.HttpResponse;

public class NotFoundHandler implements ErrorHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        return new HttpResponse().setStatus(404).setBody("NOT FOUND");
    }
}
