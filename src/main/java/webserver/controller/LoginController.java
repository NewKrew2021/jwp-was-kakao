package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.Response;
import webserver.http.ResponseStatus;

import java.util.Map;

public class LoginController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) {
        Map<String, String> entity = httpRequest.getEntity();
        User user = DataBase.findUserById(entity.get("userId"));
        if (user.getPassword().equals(entity.get("password"))) {
            Response response = new Response();
            response.setStatus(ResponseStatus.SEE_OTHER);
            response.setHeaders("Set-Cookie: logined=true; Path=/");
            response.setHeaders("Location: /index.html");
            return response;
        }

        Response response = new Response();
        response.setStatus(ResponseStatus.SEE_OTHER);
        response.setHeaders("Set-Cookie: logined=false; Path=/");
        response.setHeaders("Location: /user/login_failed.html");
        return response;
    }
}
