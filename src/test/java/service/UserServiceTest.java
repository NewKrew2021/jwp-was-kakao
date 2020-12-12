package service;

import db.DataBase;
import model.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    UserService service = new UserService();
    static User user;

    @BeforeAll
    static void setUp() {
        user = new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");
        DataBase.addUser(user);
    }

    @Test
    public void saveUser() {
        User expectedUser = new User("javajigi2", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");

        User user = service.addUser(expectedUser);

        assertThat(user).isEqualTo(expectedUser);
        assertThat(DataBase.findUserById("javajigi2")).isEqualTo(expectedUser);
        DataBase.remove("javajigi2");
    }

    @Test
    public void loginUser() {
        boolean login = service.login("javajigi", "password");

        assertThat(login).isEqualTo(true);
    }

    @Test
    public void loginNoUser() {
        boolean login = service.login("javajig", "password");

        assertThat(login).isEqualTo(false);
    }

    @Test
    public void loginInvalidPassword() {
        boolean login = service.login("javajigi", "passwod");

        assertThat(login).isEqualTo(false);
    }


    @Test
    public void findAll() {
        Collection<User> users = service.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users).contains(user);
    }
}