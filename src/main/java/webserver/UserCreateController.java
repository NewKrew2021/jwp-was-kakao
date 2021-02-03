package webserver;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import domain.URLMapper;
import model.User;

public class UserCreateController extends AbstractController {

    @Override
    void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        doPost(httpRequest, httpResponse);
    }

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        User user = new User(httpRequest.getParameter("userId"), httpRequest.getParameter("password"), httpRequest.getParameter("name"), httpRequest.getParameter("email"));
        DataBase.addUser(user);
        httpResponse.sendRedirect(URLMapper.INDEX_URL);
    }
}
