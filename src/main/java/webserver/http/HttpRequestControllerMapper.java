package webserver.http;

import webserver.http.controller.Controller;

public interface HttpRequestControllerMapper {

    void addMapping(HttpRequestMapping mapping);

    void addMapping(HttpRequestMapping... mappings);

    Controller getController(HttpRequest httpRequest);

}
