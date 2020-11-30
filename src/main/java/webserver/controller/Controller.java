package webserver.controller;

import webserver.http.HttpRequest;
import webserver.http.Response;

@FunctionalInterface
public
interface Controller {
    Response execute(HttpRequest httpRequest) throws Exception;

}
