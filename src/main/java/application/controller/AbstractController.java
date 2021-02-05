package application.controller;

import org.springframework.http.HttpMethod;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (httpRequest.getHeader("Cookie").contains("logined=true")) {
            httpResponse.addHeader("Set-Cookie", "logined=true");
        }

        if (httpRequest.getMethod().equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }

        if (httpRequest.getMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

    }

}


