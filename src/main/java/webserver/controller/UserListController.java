package webserver.controller;

import db.DataBase;
import webserver.http.HttpRequest;
import webserver.http.Response;
import webserver.http.ResponseStatus;

import java.util.Optional;

public class UserListController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) {
        if (!isLogin(httpRequest)) {
            Response response = new Response();
            response.setStatus(ResponseStatus.SEE_OTHER);
            response.setHeaders("Location: /user/login.html");
            return response;
        }

        Response response = new Response();
        response.setModel(DataBase.findAll());
        response.setViewName("user/list");
        return response;
    }

    private boolean isLogin(HttpRequest httpRequest) {
        return Optional.ofNullable(httpRequest.getCookies())
                .map(cookies -> cookies.contains("logined=true"))
                .isPresent();
    }
}
