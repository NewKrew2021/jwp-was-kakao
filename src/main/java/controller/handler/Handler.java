package controller.handler;

import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface Handler {
    HttpResponse handle(HttpRequest request, OutputStream out) throws NoFileException, IOException;
}
