package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        switch (httpRequest.getRequestMethod()) {
            case GET:
                doGet(httpRequest, httpResponse, sessionStorage);
                break;
            case POST:
                doPost(httpRequest, httpResponse, sessionStorage);
                break;
        }
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        throw new IllegalArgumentException(
                String.format("No implementation for %s as GET", this.getClass()));
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        throw new IllegalArgumentException(
                String.format("No implementation for %s as POST", this.getClass()));
    }
}
