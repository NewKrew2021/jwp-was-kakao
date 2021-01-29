package web;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HttpUrlTest {
    @Test
    void create() {
        HttpUrl httpURL = HttpUrl.of("/user/create");
        assertThat(httpURL).extracting("url").isEqualTo("/user/create");
        assertThat(httpURL).extracting("parameters").isEqualTo(new HashMap<>());
    }

    @Test
    void getParameter() {
        HttpUrl httpURL = HttpUrl.of("/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        assertThat(httpURL).extracting("url").isEqualTo("/user/create");
        assertThat(httpURL.getParameter("userId")).isEqualTo("javajigi");
        assertThat(httpURL.getParameter("password")).isEqualTo("password");
        assertThat(httpURL.getParameter("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(httpURL.getParameter("email")).isEqualTo("javajigi%40slipp.net");
    }
}