package application;

import domain.HttpRequest;
import domain.HttpResponse;

public class AbstractController implements Controller {

    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        if (httpRequest.getMethod().matches("POST")) {
            doPost(httpRequest, httpResponse);
            return;
        }
        doGet(httpRequest, httpResponse);
    }

}
