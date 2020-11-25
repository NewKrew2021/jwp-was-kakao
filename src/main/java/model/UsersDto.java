package model;

import java.util.List;
import java.util.stream.Collectors;

public class UsersDto {
    private final List<UserDto> users;

    public UsersDto(List<User> users) {
        this.users = users.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {
        return users;
    }
}
