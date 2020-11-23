package webserver.http;

import webserver.http.controller.Controller;

public interface UriMapping {
    boolean matches(String uri);

    Controller getController();
}
