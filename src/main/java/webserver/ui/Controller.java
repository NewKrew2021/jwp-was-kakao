package webserver.ui;

import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage);
}
