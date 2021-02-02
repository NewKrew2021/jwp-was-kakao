package controller.handler;

import model.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

@FunctionalInterface
public interface Handler {
    void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException;
}
