package controller;

import exception.utils.NoFileException;
import model.Constant;
import model.HttpRequest;
import model.HttpResponse;
import model.httpinfo.HttpMethod;

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
        return new HttpResponse().forward(Constant.STATIC_PATH, request.getPath());
    }

    public HttpResponse handleView(HttpRequest request) throws NoFileException {
        return new HttpResponse().forward(Constant.TEMPLATES_PATH,
                (request.getPath().equals("/") ? Constant.INDEX_HTML : request.getPath()));
    }
}
