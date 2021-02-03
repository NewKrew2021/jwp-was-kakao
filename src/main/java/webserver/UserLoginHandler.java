package webserver;

import db.DataBase;
import domain.HttpRequest;
import domain.HttpResponse;
import model.User;

public class UserLoginHandler extends AbstractHandler{

    @Override
    void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String id = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        User user = DataBase.findUserById(id);
        if (user == null || !user.getPassword().equals(password)) {
            httpResponse.loginFalse();
            return;
        }
        httpResponse.loginTrue();
        return;
    }
}
