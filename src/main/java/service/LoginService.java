package service;

import db.DataBase;
import model.User;
import request.HttpRequest;
import session.Sessions;

import javax.security.auth.login.LoginException;

public class LoginService {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    public String login(HttpRequest httpRequest) throws LoginException {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(PASSWORD);
        User user = DataBase.findUserById(userId).orElseThrow(LoginException::new);
        if(isValidUserInfo(user, password)){
            return Sessions.add(user);
        }
        throw new LoginException();
    }

    public boolean isValidUserInfo(User user, String password){
        return user.isSamePassword(password);
    }
}
