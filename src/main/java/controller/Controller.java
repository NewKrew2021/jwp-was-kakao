package controller;

import http.HttpRequest;

import java.util.Optional;

public abstract class Controller {
    abstract Optional<Handler> getResponsibleHandler(HttpRequest request);
}
