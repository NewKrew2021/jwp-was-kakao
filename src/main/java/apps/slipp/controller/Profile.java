package apps.slipp.controller;

import apps.slipp.model.User;

public class Profile {

    private String userId;
    private String name;

    public Profile(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public static Profile of(User user) {
        return new Profile(user.getUserId(), user.getName());
    }
}
