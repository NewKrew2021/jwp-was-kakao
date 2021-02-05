package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;
import webserver.ui.AbstractController;

public class ForwardController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        httpResponse.forward(httpRequest.getPath());
    }
}
