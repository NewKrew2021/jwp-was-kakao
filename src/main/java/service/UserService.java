package service;

import db.DataBase;
import model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void create(User user) {
        DataBase.addUser(user);
    }
}
