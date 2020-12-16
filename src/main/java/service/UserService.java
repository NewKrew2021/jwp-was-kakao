package service;

import db.DataBase;
import model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    public User addUser(User user) {
        DataBase.addUser(user);
        return user;
    }

    public boolean login(String userId, String password) {
        return Optional.ofNullable(DataBase.findUserById(userId))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
