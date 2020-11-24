package webserver.http;

import webserver.http.controller.Controller;

public interface HttpRequestControllerMapper {

    void addMapping(RegexpMapping mapping);

    void addMapping(RegexpMapping... mappings);

    Controller getController(HttpRequest httpRequest);

}
