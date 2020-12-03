package apps.slipp.service;

import apps.slipp.db.DataBase;
import apps.slipp.model.User;

public class SignUpService {

    public void signUp( User user ){
        if( DataBase.findUserById(user.getUserId()) != null )
            throw new UserAlreadySignUpException(user);

        DataBase.addUser(user);
    }

}
