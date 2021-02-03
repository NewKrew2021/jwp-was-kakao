package webserver;

import db.DataBase;
import domain.HttpRequest;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {

    @BeforeEach
    void setUp() {
        DataBase.addUser(new User("id","pw","user","user@kakaocorp.com"));
    }

    @Test
    @DisplayName("로그인 성공시 logined=true 쿠키가 생성된다.")
    void check_login_token() {
        String input = "POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n\n" +
                "userId=id&password=pw";

        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        try {
            HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(stream)));
//            assertThat(request.getHeaders().get("Cookie")).isEqualTo("logined=true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
