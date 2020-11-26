package webserver.http.dispatcher;

import webserver.http.HttpRequest;
import webserver.http.Controller;

public interface HttpRequestMapping {

    boolean matches(HttpRequest httpRequest);

    Controller getController();
}
