package webserver;

import domain.HttpRequest;
import domain.HttpResponse;

public interface Handler {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}

