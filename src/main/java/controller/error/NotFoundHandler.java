package controller.error;

import controller.handler.ErrorHandler;
import model.HttpRequest;
import model.HttpResponse;
import model.httpinfo.HttpStatusMessage;

public class NotFoundHandler implements ErrorHandler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        int status = 404;
        return new HttpResponse().setStatus(status).setBody(HttpStatusMessage.of(status));
    }
}
