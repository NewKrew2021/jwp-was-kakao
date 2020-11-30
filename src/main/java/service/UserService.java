package service;

import db.DataBase;
import model.User;

public class UserService {

    public static void addNewUser(String userId, String password, String name, String email) {
        DataBase.addUser(new User(userId, password, name, email));
    }

}
