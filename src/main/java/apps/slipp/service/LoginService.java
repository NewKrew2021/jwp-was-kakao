package apps.slipp.service;

import db.DataBase;
import model.User;

public class LoginService {

    public void login(String userId, String password) {
        User user = getUser(userId);
        if (user == null) throw new NotExistUserException(userId);
        if (!user.getPassword().equals(password)) throw new InvalidPasswordException(userId);
    }

    private User getUser(String userId) {
        return DataBase.findUserById(userId);
    }
}
