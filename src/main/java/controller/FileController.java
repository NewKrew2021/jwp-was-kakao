package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import webserver.URLMapper;

public class FileController extends AbstractController {


    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        if(path.equals(URLMapper.ROOT_URL)) {
            path = URLMapper.INDEX_URL;
        }
        httpResponse.forward(path);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        doGet(httpRequest, httpResponse);
    }
}
