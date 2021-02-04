package service;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.Map;

public class UserService {
    public void addUser(Map<String, String> bodyParsed) {
        User user = new User(
                bodyParsed.get("userId"),
                bodyParsed.get("password"),
                bodyParsed.get("name"),
                bodyParsed.get("email")
        );
        DataBase.addUser(user);
    }

    public User findUser(Map<String, String> bodyParsed) {
        return DataBase.findUserById(bodyParsed.get("userId"));
    }

    public Collection<User> findAll() {
        return DataBase.findAll();
    }

    public boolean login(Map<String, String> parsedBody) {
        return findUser(parsedBody) != null;
    }
}
