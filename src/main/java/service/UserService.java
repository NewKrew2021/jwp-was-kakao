package service;

import db.DataBase;
import dto.ParamValue;
import model.User;
import model.factory.UserFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void create(ParamValue userMap) {
        User user = UserFactory.create(userMap);
        DataBase.addUser(user);
    }

}
