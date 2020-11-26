package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;

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

        User user = service.addUser(new HashMap<String, String>() {{
            put("userId", "javajigi2");
            put("password", "password");
            put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
            put("email", "javajigi%40slipp.net");
        }});

        assertThat(user).isEqualTo(expectedUser);
        assertThat(DataBase.findUserById("javajigi2")).isEqualTo(expectedUser);
        DataBase.remove("javajigi2");
    }

    @Test
    public void loginUser() {
        boolean login = service.login(new HashMap<String, String>() {{
            put("userId", "javajigi");
            put("password", "password");
        }});

        assertThat(login).isEqualTo(true);
    }

    @Test
    public void loginNoUser() {
        boolean login = service.login(new HashMap<String, String>() {{
            put("userId", "javajig");
            put("password", "password");
        }});

        assertThat(login).isEqualTo(false);
    }

    @Test
    public void loginInvalidPassword() {
        boolean login = service.login(new HashMap<String, String>() {{
            put("userId", "javajigi");
            put("password", "passwod");
        }});

        assertThat(login).isEqualTo(false);
    }


    @Test
    public void findAll() {
        Collection<User> users = service.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users).contains(user);
    }
}