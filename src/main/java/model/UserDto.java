package model;

public class UserDto {
    private User user;

    public UserDto(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
