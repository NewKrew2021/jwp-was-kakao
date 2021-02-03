package webserver;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

public class UserCreateHandler extends AbstractHandler {

    final String INDEX = "/index.html";

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        doPost(httpRequest, httpResponse);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect(INDEX);
    }
}
