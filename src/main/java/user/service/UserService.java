package user.service;

import db.DataBase;
import user.model.User;
import user.vo.UserCreateValue;

import java.util.Collection;

public class UserService {
    public static void insert(UserCreateValue values) {
        DataBase.addUser(new User(values.getUserId(), values.getPassword(), values.getName(), values.getEmail()));
    }

    public static Collection<User> findAll() {
        return DataBase.findAll();
    }

    public static User findById(String userId) {
        return DataBase.findUserById(userId);
    }
}
