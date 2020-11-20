package service;

import db.DataBase;
import model.User;

import java.util.Map;

public class UserService {

    public User addUser(Map<String, String> attributeMap) {
        User user = new User(attributeMap.get("userId"), attributeMap.get("password"), attributeMap.get("name"), attributeMap.get("email"));
        DataBase.addUser(user);
        return user;
    }
}
