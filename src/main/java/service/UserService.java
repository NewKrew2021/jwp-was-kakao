package service;

import db.DataBase;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService {

    public User addUser(Map<String, String> attributeMap) {
        User user = new User(attributeMap.get("userId"), attributeMap.get("password"), attributeMap.get("name"), attributeMap.get("email"));
        DataBase.addUser(user);
        return user;
    }

    public boolean login(Map<String, String> attributeMap) {
        return Optional.ofNullable(DataBase.findUserById(attributeMap.get("userId")))
                .map(user -> user.getPassword().equals(attributeMap.get("password")))
                .orElse(false);
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
