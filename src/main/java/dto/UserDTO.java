package dto;

import model.User;

public class UserDTO {

    private final String userId;
    private final String name;
    private final String email;

    private UserDTO(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static UserDTO of(User user) {
        return new UserDTO(user.getUserId(), user.getName(), user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
