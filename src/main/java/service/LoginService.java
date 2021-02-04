package service;

import db.DataBase;
import model.User;
import request.HttpRequest;
import java.util.Optional;

public class LoginService {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    public boolean login(HttpRequest httpRequest){
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(PASSWORD);
        Optional<User> user = DataBase.findUserById(userId);
        return user.isPresent() && user.get().isSamePassword(password);
    }
}
