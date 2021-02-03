package webserver;

import domain.HttpRequest;
import domain.HttpResponse;

public class FileHandler extends AbstractHandler{
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }
}
