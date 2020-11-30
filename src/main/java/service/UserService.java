package service;

import db.DataBase;
import model.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    public void create(User user) {
        DataBase.addUser(user);
    }

    public boolean isLogin(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (Objects.nonNull(user) && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
