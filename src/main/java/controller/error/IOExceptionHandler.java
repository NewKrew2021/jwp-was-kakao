package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;
import model.response.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;

public class IOExceptionHandler implements Handler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).sendFile("./templates", "/500.html").ok();
    }
}
