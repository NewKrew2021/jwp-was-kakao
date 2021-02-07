package controller;

import exception.NotExistException;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public HttpResponse service(HttpRequest request) {
        if (request.getMethod() == HttpMethod.POST) {
            return doPost(request);
        }
        if (request.getMethod() == HttpMethod.GET) {
            return doGet(request);
        }
        throw new NotExistException("요청에 매칭되는 동작이 없습니다.");
    }

    public abstract boolean match(String path);

    public abstract String getPath();

    public abstract HttpResponse doPost(HttpRequest request);

    public abstract HttpResponse doGet(HttpRequest request);
}
