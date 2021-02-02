package controller.handler;

import exception.utils.NoFileException;
import model.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface Handler {
    void handle(HttpRequest request, OutputStream out) throws NoFileException, IOException;
}
