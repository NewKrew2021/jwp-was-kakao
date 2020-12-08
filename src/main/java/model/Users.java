package model;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private final List<User> users = new ArrayList<>();

    public Users(List<User> items) {
        for (User user : items) {
            users.add(user);
        }
    }

    public List<User> getUsers() {
        return users;
    }
}
