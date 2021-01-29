package service;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginServiceTest {
    private LoginService loginService;

    @Test
    void createUser() {
        loginService = new LoginService();
        Map<String, String> argument = new HashMap<>();
        argument.put("userId", "test_id");
        argument.put("password", "test_pw");
        argument.put("name", "test_name");
        argument.put("email", "test_email");

        loginService.createUser(argument);
        User actual = DataBase.findUserById("test_id");
        assertThat(actual.getName()).isEqualTo("test_name");
    }
}
