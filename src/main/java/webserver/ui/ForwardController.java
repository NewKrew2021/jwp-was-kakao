package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class ForwardController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
