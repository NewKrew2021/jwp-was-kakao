package controller;

import exception.MethodMappingException;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

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
        throw new MethodMappingException(
                String.format("잘못된 요청입니다: %s %s", httpRequest.getMethod().name(), httpRequest.getPath()));
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        throw new MethodMappingException(
                String.format("잘못된 요청입니다: %s %s", httpRequest.getMethod().name(), httpRequest.getPath()));
    }

}
