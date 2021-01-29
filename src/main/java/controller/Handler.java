package controller;

import model.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

@FunctionalInterface
interface Handler {
    void handle(HttpRequest request, OutputStream out) throws URISyntaxException, IOException;
}
