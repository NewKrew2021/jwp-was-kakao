package service;

import db.DataBase;
import model.User;

import java.util.Collection;

public class UserService {

    public static void addNewUser(String userId, String password, String name, String email) {
        DataBase.addUser(new User(userId, password, name, email));
    }

    public static boolean isLoginSuccessful(String userId, String password) {
        User user = DataBase.findUserById(userId);

        if (user == null) {
            return false;
        }

        return user.getPassword().equals(password);
    }

    public static Collection<User> getAllUsers() {
        return DataBase.findAll();
    }

}
