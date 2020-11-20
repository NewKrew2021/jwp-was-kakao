package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceTest {
    UserService service = new UserService();

    @Test
    public void saveUser() {
        User expectedUser = new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");

        User user = service.addUser(new HashMap<String, String>() {{
            put("userId", "javajigi");
            put("password", "password");
            put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
            put("email", "javajigi%40slipp.net");
        }});

        assertThat(user).isEqualTo(expectedUser);
        assertThat(DataBase.findUserById("javajigi")).isEqualTo(expectedUser);
    }
}