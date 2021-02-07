package handler;

import db.DataBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import web.HttpHeaders;
import web.HttpRequest;
import web.HttpResponse;
import webserver.HttpServlet;

import static handler.UserLoginHandler.LOGIN;

public class UserListHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Boolean login = (Boolean) httpRequest.getHttpSession().getAttribute(LOGIN);
        if (isNotLogined(login)) {
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

    private boolean isNotLogined(Boolean isLogined) {
        return isLogined == null || !isLogined;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        return httpRequest.getHttpUrl().hasSameUrl("/user/list") && httpRequest.hasSameMethod(HttpMethod.GET);
    }
}
