package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

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
        throw new IllegalArgumentException(
                String.format("No implementation for %s as GET", this.getClass()));
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new IllegalArgumentException(
                String.format("No implementation for %s as POST", this.getClass()));
    }
}
