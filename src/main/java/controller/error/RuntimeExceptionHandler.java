package controller.error;

import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

public class RuntimeExceptionHandler implements Handler {
    @Override
    public void handle(HttpRequest request, OutputStream out) throws NoFileException, IOException {
        HttpResponse.of(out).setStatus(400).sendFile("./templates", "/400.html").ok();
    }
}
