package webserver.http;

import webserver.http.controller.Controller;

public interface HttpRequestControllerMapper {

    void addMapping(RegexpUriMapping mapping);

    Controller getController(HttpRequest httpRequest);

}
