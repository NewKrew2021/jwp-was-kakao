package webserver.controller;

import db.DataBase;
import model.User;
import webserver.http.HttpRequest;
import webserver.http.Response;
import webserver.http.ResponseStatus;

public class CreateUserController implements Controller {
    @Override
    public Response execute(HttpRequest httpRequest) {
        User user = User.createUser(httpRequest.getEntity());
        DataBase.addUser(user);
        Response response = new Response();
        response.setStatus(ResponseStatus.SEE_OTHER);
        response.setHeaders("Location: /index.html");
        return response;
    }
}
