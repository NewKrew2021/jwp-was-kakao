package apps.slipp.service;

import apps.slipp.db.DataBase;
import apps.slipp.model.User;

public class UserService {

    public User get(String userId){
        return DataBase.findUserById(userId);
    }

}
