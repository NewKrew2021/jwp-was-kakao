package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.FileIOException;

import java.net.URISyntaxException;

public class FileController extends AbstractController {
    private static final String ROOT_URL = "/";
    private static final String INDEX_URL = "/index.html";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws FileIOException, URISyntaxException {
        String path = httpRequest.getPath();
        if(path.equals(ROOT_URL)) {
            path = INDEX_URL;
        }
        httpResponse.forward(path);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws FileIOException, URISyntaxException {
        doGet(httpRequest, httpResponse);
    }

    @Override
    public boolean isSupport(String path) {
        return true;
    }
}
