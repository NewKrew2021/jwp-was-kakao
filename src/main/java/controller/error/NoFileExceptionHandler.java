package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

public class NoFileExceptionHandler implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) throws NoFileException {
        return new HttpResponse().setStatus(404).sendFile("./templates", "/404.html");
    }
}
