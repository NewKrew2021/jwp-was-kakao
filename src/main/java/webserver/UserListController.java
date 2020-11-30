package webserver;

import db.DataBase;

import java.util.Optional;

public class UserListController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) {
        if (!isLogin(httpRequest)) {
            Response response = new Response();
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
