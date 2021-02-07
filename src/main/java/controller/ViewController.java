package controller;

import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;

public class ViewController extends Controller {

    {
        setBasePath("");
        putHandler("/js/.*", "GET", this::handleFile);
        putHandler("/css/.*", "GET", this::handleFile);
        putHandler("/fonts/.*", "GET", this::handleFile);
        putHandler("/favicon.ico", "GET", this::handleFile);
        putHandler("/index.html", "GET", this::handleView);
        putHandler("/", "GET", this::handleView);
    }

    public void handleFile(HttpRequest request, HttpResponse response) throws NoFileException {
        response.forward("./static", request.getPath());
    }

    public void handleView(HttpRequest request, HttpResponse response) throws NoFileException {
        response.forward("./templates",
                (request.getPath().equals("/") ? "/index.html" : request.getPath()));
    }
}
