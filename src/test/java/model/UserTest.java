package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Request;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("유저를 올바르게 생성")
    void create_user() {
        String input = "GET /user/create?userId=javajigi&password=password&name=자바지기&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: *\n";

        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        try {
            Request request = new Request(new BufferedReader(new InputStreamReader(stream)));
            User user = new User(request.getParameters());
            assertThat(user.getUserId()).isEqualTo("javajigi");
            assertThat(user.getPassword()).isEqualTo("password");
            assertThat(user.getName()).isEqualTo("자바지기");
            assertThat(user.getEmail()).isEqualTo("javajigi%40slipp.net");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}