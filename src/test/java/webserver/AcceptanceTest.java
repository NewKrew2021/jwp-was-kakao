package webserver;

import db.DataBase;
import org.junit.jupiter.api.Test;
import webserver.user.UserRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {
    @Test
    void addUser() {
        Request request = Request.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=email%40email.com");
        UserRequest userRequest = UserRequest.of(request.getUserRequestParam());
        DataBase.addUser(userRequest.toUser());

        assertThat(DataBase.findAll()).hasSize(1);
    }
}
