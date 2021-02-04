package controller;

import exception.utils.NoFileException;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;

public class ViewController extends Controller {

    {
        setBasePath("");
        putHandler("/js/.*", HttpMethod.GET, this::handleFile);
        putHandler("/css/.*", HttpMethod.GET, this::handleFile);
        putHandler("/fonts/.*", HttpMethod.GET, this::handleFile);
        putHandler("/favicon.ico", HttpMethod.GET, this::handleFile);
        putHandler("/.*", HttpMethod.GET, this::handleView);
    }

    public HttpResponse handleFile(HttpRequest request) throws NoFileException {
        return new HttpResponse().forward("./static", request.getPath());
    }

    public HttpResponse handleView(HttpRequest request) throws NoFileException {
        return new HttpResponse().forward("./templates",
                (request.getPath().equals("/") ? "/index.html" : request.getPath()));
    }
}
