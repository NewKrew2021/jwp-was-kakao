package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse);
}
