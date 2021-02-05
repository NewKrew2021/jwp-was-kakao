package application.user;

import db.DataBase;
import model.User;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

public class UserService {

    public static void createUser(User user) {
        DataBase.addUser(user);
    }

    public static User findUserById(String userId) {
        return DataBase.findUserById(userId);
    }

    public static Collection<User> getAllUser() {
        return DataBase.findAll();
    }

    public static boolean login(String userId, String password) {
        User user = DataBase.findUserById(userId);
        if (!ObjectUtils.isEmpty(user)) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
