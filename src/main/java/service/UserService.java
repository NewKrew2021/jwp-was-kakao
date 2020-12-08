package service;

import db.DataBase;
import model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    public void create(User user) {
        DataBase.addUser(user);
    }

    public boolean isLogin(String userId, String password) {
        User user = DataBase.findUserById(userId);
        return Objects.nonNull(user) && user.getPassword().equals(password);
    }

    public List<User> getList() {
        return new ArrayList<>(DataBase.findAll());
    }
}
