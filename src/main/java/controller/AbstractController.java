package controller;

import exception.MethodMappingException;
import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class AbstractController implements Controller {

    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        if (HttpMethod.GET.equals(httpRequest.getMethod())) {
            doGet(httpRequest, httpResponse);
            return;
        }
        if (HttpMethod.POST.equals(httpRequest.getMethod())) {
            doPost(httpRequest, httpResponse);
            return;
        }
        throw new MethodMappingException(String.format("지원되지 않는 메서드입니다: %s", httpRequest.getMethod().name()));
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
    }

}
