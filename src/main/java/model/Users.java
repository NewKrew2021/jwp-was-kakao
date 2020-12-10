package model;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private final List<User> users = new ArrayList<>();

    private Users(List<User> items) {
        for (User user : items) {
            users.add(user);
        }
    }

    public static Users from(List<User> items){
        return new Users(items);
    }
    public List<User> getUsers() {
        return users;
    }
}
