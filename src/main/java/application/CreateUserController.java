package application;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

public class CreateUserController extends AbstractController {

    public static final String INDEX_HTML = "/index.html";

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        DataBase.addUser(new User(httpRequest.getParameters()));
        httpResponse.sendRedirect(INDEX_HTML);
    }
}