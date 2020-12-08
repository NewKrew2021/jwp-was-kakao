package webserver.user;

import db.DataBase;
import model.User;
import model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {

    User user;
    UserService userService;

    @BeforeEach
    public void setUp(){
        user = new User("1", "passwd", "test", "test@dd.net");
        DataBase.addUser(user);
        userService = new UserService();
    }

    @Test
    public void addUserTest() {
        userService.addUser(user);
        assertThat(user.equals(DataBase.findUserById("1")));
    }

    @ParameterizedTest
    @CsvSource(value = {"1,passwd"}, delimiter = ',')
    public void loginProcess_성공(String userId, String password) {
        assertThat(userService.loginProcess(userId, password)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1,passwd2"}, delimiter = ',')
    public void loginProcess_실패(String userId, String password) {
        assertThat(userService.loginProcess(userId, password)).isFalse();
    }

    @Test
    public void findAll(){
        Users users = userService.findAll();
        assertThat(users.getUsers().contains(user)).isTrue();
    }
}
