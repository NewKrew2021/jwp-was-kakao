package controller;

import exception.utils.NoFileException;
import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;

import java.io.OutputStream;

public class ViewController extends Controller {

    {
        setBasePath("");
        putHandler("/js/.*", HttpMethod.GET, this::handleFile);
        putHandler("/css/.*", HttpMethod.GET, this::handleFile);
        putHandler("/fonts/.*", HttpMethod.GET, this::handleFile);
        putHandler("/favicon.ico", HttpMethod.GET, this::handleFile);
        putHandler("/.*", HttpMethod.GET, this::handleView);
    }

    public HttpResponse handleFile(HttpRequest request, OutputStream out) throws NoFileException {
        return HttpResponse.of(out).forward("./static", request.getPath());
    }

    public HttpResponse handleView(HttpRequest request, OutputStream out) throws NoFileException {
        return HttpResponse.of(out).forward("./templates",
                (request.getPath().equals("/") ? "/index.html" : request.getPath()));
    }
}
