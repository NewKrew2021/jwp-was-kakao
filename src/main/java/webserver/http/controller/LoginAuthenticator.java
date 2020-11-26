package webserver.http.controller;

import db.DataBase;
import model.User;

public class LoginAuthenticator {

    public void authenticate(String userId, String password) {
        User user = getUser(userId);
        if (user == null) throw new NotExistUserException(userId);
        if (!user.getPassword().equals(password)) throw new InvalidPasswordException(userId);
    }

    private User getUser(String userId) {
        return DataBase.findUserById(userId);
    }
}
