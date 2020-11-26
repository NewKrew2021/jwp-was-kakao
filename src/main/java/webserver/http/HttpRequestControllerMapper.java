package webserver.http;

import webserver.http.dispatcher.HttpRequestMapping;

public interface HttpRequestControllerMapper {

    void addMapping(HttpRequestMapping mapping);

    void addMapping(HttpRequestMapping... mappings);

    Controller getController(HttpRequest httpRequest);

}
