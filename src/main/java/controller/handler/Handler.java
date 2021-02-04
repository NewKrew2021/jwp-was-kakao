package controller.handler;

import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;

@FunctionalInterface
public interface Handler {
    HttpResponse handle(HttpRequest request) throws NoFileException, IOException;
}
