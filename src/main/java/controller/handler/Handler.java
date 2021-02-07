package controller.handler;

import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.IOException;

@FunctionalInterface
public interface Handler {
    void handle(HttpRequest request, HttpResponse response) throws NoFileException, IOException;
}
