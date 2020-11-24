package webserver.http;

import webserver.http.controller.Controller;

public interface HttpRequestMapping {

    boolean matches(HttpRequest httpRequest);

    Controller getController();
}
