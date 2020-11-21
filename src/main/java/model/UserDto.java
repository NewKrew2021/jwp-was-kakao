package model;

public class UserDto {
    private int index;
    private User user;

    public UserDto(int index, User user) {
        this.index = index;
        this.user = user;
    }

    public int getIndex() {
        return index;
    }

    public User getUser() {
        return user;
    }
}
