package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Handler {
    HttpResponse handleRequest(HttpRequest request) throws IOException, URISyntaxException;
}
