package handler;

import db.DataBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import web.HttpHeaders;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpUrl;
import webserver.HttpServlet;

public class UserListHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String cookie = httpRequest.getHttpHeaders().get("Cookie");
        if (cookie == null || !cookie.equals("logined=true")) {
            HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
            httpResponse.addHeader(HttpHeaders.LOCATION, "/user/login.html");
            return httpResponse;
        }

        String body = FileIoUtils.loadHandleBarFromClasspath("user/list", DataBase.findAll());
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK);

        httpResponse.addHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length()));
        httpResponse.setBody(body);

        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/list") && httpRequest.hasSameMethod(HttpMethod.GET);
    }
}
