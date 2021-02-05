package user.service;

import db.DataBase;
import user.model.User;
import user.vo.UserCreateValue;
import webserver.domain.HttpSession;

import java.util.Collection;

public class UserService {
    public static void insert(UserCreateValue values) {
        User user = new User(values.getUserId(), values.getPassword(), values.getName(), values.getEmail());
        DataBase.addUser(user);
    }

    public static Collection<User> findAll() {
        return DataBase.findAll();
    }

    public static User findById(String userId) {
        return DataBase.findUserById(userId);
    }
}
