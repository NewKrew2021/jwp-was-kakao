package webserver;

import domain.HttpRequest;
import domain.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}

