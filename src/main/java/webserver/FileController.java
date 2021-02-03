package webserver;

import domain.HttpRequest;
import domain.HttpResponse;

public class FileController extends AbstractController {
    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = httpRequest.getPath();
        if(path.equals("/")) {
            path = "/index.html";
        }
        httpResponse.forward(path);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        doGet(httpRequest, httpResponse);
    }
}
