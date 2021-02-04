package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

public class RuntimeExceptionHandler implements Handler {
    @Override
    public void handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException {
        response.setStatus(400).sendFile("./templates", "/400.html").ok();
    }
}
