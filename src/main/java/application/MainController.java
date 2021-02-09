package application;

import domain.ContentType;
import domain.HttpRequest;
import domain.HttpResponse;

public class MainController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String url = httpRequest.getUrl();
        url = ContentType.prefixOfUrl(url) + url;
        httpResponse.forward(url);
    }

}
