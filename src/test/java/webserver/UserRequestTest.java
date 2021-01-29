package webserver;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.Test;
import webserver.user.UserRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestTest {
    @Test
    void parseUserRequestParam() {
        UserRequest userRequest = UserRequest.of("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=email%40email.com");

        assertThat(userRequest.getUserId()).isEqualTo("javajigi");
        assertThat(userRequest.getPassword()).isEqualTo("password");
        assertThat(userRequest.getName()).isEqualTo("박재성");
        assertThat(userRequest.getEmail()).isEqualTo("email@email.com");
    }
}
