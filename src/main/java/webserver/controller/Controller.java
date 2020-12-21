package webserver.controller;

import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpStatus;

import java.io.IOException;

public interface Controller {
    String getPath();

    default void handle(HttpRequest request, HttpResponse response) throws IOException {
        response.sendHeader(HttpStatus.NOT_FOUND);
    }
}
