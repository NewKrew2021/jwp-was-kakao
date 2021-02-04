package service;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Optional;

public class UserService {

    public static void createUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        DataBase.addUser(user);
    }

    public static Collection<User> findAllUser() {
        return DataBase.findAll();
    }

    public static boolean isLoginSuccess(String userId, String password) {
        Optional<User> user = DataBase.findUserById(userId);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

}
