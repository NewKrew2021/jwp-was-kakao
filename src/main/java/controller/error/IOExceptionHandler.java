package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

public class IOExceptionHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) throws NoFileException {
        return new HttpResponse().setStatus(500).sendFile("./templates", "/500.html");
    }
}
