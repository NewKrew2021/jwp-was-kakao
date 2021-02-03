package webserver.http;

import annotation.web.ResponseStatus;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        switch (httpRequest.getMethod()) {
            case "GET":
                doGet(httpRequest, httpResponse);
                break;
            case "POST":
                doPost(httpRequest, httpResponse);
                break;
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.sendStatus(ResponseStatus.METHOD_NOT_ALLOWED);
    }
}
