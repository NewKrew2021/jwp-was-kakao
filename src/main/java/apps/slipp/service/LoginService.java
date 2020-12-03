package apps.slipp.service;

import apps.slipp.db.DataBase;
import apps.slipp.model.User;

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
