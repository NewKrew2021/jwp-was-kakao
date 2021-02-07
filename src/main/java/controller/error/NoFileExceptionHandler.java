package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;

public class NoFileExceptionHandler implements Handler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException {
        response.setStatus(HttpStatus.NOT_FOUND).sendFile("./templates", "/404.html").ok();
    }
}
