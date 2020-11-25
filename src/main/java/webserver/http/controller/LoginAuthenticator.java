package webserver.http.controller;

import db.DataBase;
import model.User;

public class LoginAuthenticator {

    public boolean authenticate(String userId, String password) {
        User user = getUser(userId);
        if (user == null) return false;
        if (!user.getPassword().equals(password)) return false;
        return true;
    }

    private User getUser(String userId) {
        return DataBase.findUserById(userId);
    }
}
