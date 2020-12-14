package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface HttpController{

    HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException;
}

