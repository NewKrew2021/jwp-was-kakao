package model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UsersDto {
    private final List<UserDto> users;

    public UsersDto(List<User> users) {
        this.users = IntStream.range(0, users.size())
                .mapToObj(i -> new UserDto(i + 1, users.get(i)))
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {
        return users;
    }
}
