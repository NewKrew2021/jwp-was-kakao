package service;

import db.DataBase;
import model.User;
import request.HttpRequest;
import session.Sessions;

import javax.security.auth.login.LoginException;
import java.util.Optional;

public class LoginService {

    Sessions sessions = new Sessions();
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    public String login(HttpRequest httpRequest) throws LoginException {
        String userId = httpRequest.getParameter(USER_ID);
        String password = httpRequest.getParameter(PASSWORD);
        Optional<User> user = DataBase.findUserById(userId);
        if(isValidUserInfo(user, password)){
            String sessionId = sessions.add(user.get());
            return sessionId;
        }
        throw new LoginException();
    }

    public boolean isValidUserInfo(Optional<User> user, String password){
        return user.isPresent() && user.get().isSamePassword(password);
    }
}
