package controller;

import model.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface Controller {
    boolean hasSameBasePath(String path);

    boolean handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException;
}
