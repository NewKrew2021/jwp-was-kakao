package controller.error;

import controller.handler.ErrorHandler;
import model.HttpRequest;
import model.HttpResponse;

public class IOExceptionHandler implements ErrorHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        return new HttpResponse().setStatus(500).setBody("INTERNAL SERVER ERROR");
    }
}
