package application;

import domain.HttpRequest;
import domain.HttpResponse;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpRequest.getMethod().matches("POST")) {
            doPost(httpRequest, httpResponse);
            return;
        }
        doGet(httpRequest, httpResponse);
    }

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {}

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {}

}
