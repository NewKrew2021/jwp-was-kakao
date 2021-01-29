package service;

import annotation.Service;
import db.DataBase;
import model.User;

import java.util.Map;

@Service
public class LoginService {

    public LoginService(){}

    public void createUser(Map<String, String> argument) {
        User user = new User(
                argument.get("userId"),
                argument.get("password"),
                argument.get("name"),
                argument.get("email")
        );
        DataBase.addUser(user);
    }
}
