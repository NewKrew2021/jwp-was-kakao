package webserver.controller;

import org.springframework.http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {

    protected boolean login = false;

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        System.out.println(httpRequest.getHeader("Cookie"));
        if (httpRequest.getHeader("Cookie").equals("logined=true")) {
            login = true;
        }

        if (httpRequest.getMethod().equals(HttpMethod.POST)) {
            doPost(httpRequest, httpResponse);
        }

        if (httpRequest.getMethod().equals(HttpMethod.GET)) {
            doGet(httpRequest, httpResponse);
        }
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

    }

}


