package service;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    public static Optional<User> save(User user) {
        if(!user.isFilledCreateForm()){
            return Optional.empty();
        }
        return DataBase.addUser(user);
    }

    public static Optional<User> findByUserId(String userId) {
        return DataBase.findUserById(userId);
    }

    public static Collection<User> findAll() {
        return DataBase.findAll();
    }
}
