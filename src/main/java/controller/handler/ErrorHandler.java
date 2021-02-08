package controller.handler;

import model.HttpRequest;
import model.HttpResponse;

public interface ErrorHandler {
    HttpResponse handle(HttpRequest request);
}
