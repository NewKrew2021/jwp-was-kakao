package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHandlerTest {
    @Test
    void handleUserCreate() {
        HttpRequest httpRequest = createRequest("/usr/create");
        httpRequest.setEntity(ImmutableMap.of("userId", "red"));
        assertThat(RequestHandler.handleUserCreate(httpRequest).getLocation())
                .isEqualTo("/index.html");
    }

    @Test
    void handleLoginSuccess() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "1234"));
        assertThat(RequestHandler.handleLogin(httpRequest).getHeaders())
                .containsExactly("Set-Cookie: logined=true; Path=/", "Location: /index.html");
    }

    @Test
    void handleLoginFailed() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "0000"));
        assertThat(RequestHandler.handleLogin(httpRequest).getHeaders())
                .containsExactly("Set-Cookie: logined=false; Path=/", "Location: /user/login_failed.html");
    }

    private HttpRequest createRequest(String uri) {
        return new HttpRequest("POST", uri, "HTTP 1.1");
    }
}
