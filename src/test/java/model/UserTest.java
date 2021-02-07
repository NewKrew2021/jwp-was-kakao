package model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void mapOf() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "javajigi");
        parameters.put("password", "password");
        parameters.put("name", "%EB%B0%95%EC%9E%AC%EC%84%B1");
        parameters.put("email", "javajigi%40slipp.net");

        User user = User.mapOf(parameters);

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getName()).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
    }
}