package webserver.controller;

import db.DataBase;
import model.User;
import webserver.RequestHandler;
import webserver.model.*;

public class UserCreateController implements Controller {

    private static final String path = "/user/create";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getParameter(Parameter.USERID),
                httpRequest.getParameter(Parameter.PASSWORD),
                httpRequest.getParameter(Parameter.NAME),
                httpRequest.getParameter(Parameter.EMAIL)
        );
        DataBase.addUser(user);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.LOCATION, RequestHandler.BASE_URL);

        return httpResponse;
    }
}
