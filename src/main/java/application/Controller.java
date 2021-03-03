package application;

import domain.HttpRequest;
import domain.HttpResponse;

public interface Controller {
    void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;
}