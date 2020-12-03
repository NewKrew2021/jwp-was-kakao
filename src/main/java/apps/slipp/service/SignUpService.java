package apps.slipp.service;

import db.DataBase;
import model.User;

public class SignUpService {

    public void signUp( User user ){
        if( DataBase.findUserById(user.getUserId()) != null )
            throw new UserAlreadySignUpException(user);

        DataBase.addUser(user);
    }

}
