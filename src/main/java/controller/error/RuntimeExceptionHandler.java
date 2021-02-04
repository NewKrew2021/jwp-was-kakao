package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

public class RuntimeExceptionHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) throws NoFileException {
        return new HttpResponse().setStatus(400).sendFile("./templates", "/400.html");
    }
}
