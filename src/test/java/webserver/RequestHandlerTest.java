package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestHandlerTest {
    @Test
    void handleUserCreate() {
        HttpRequest httpRequest = createRequest("/usr/create");
        httpRequest.setEntity(ImmutableMap.of("userId", "red"));
        assertThat(RequestHandler.handleUserCreate(httpRequest).getHeaders())
                .containsExactly("Location: /index.html");
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

    @Test
    void handleListWhenLoggedIn() {
        HttpRequest httpRequest = createRequest("/user/list");
        httpRequest.addHeaders(ImmutableMap.of("Cookie", "logined=true"));
        Response response = RequestHandler.handleList(httpRequest);
        assertAll(() -> assertThat(response.getViewName()).isEqualTo("user/list"),
                  () -> assertThat(response.getModel()).isNotNull());
    }

    @Test
    void handleListWhenNotLoggedIn() {
        HttpRequest httpRequest = createRequest("/user/list");
        assertThat(RequestHandler.handleList(httpRequest).getHeaders())
                .containsExactly("Location: /user/login.html");
    }

    private HttpRequest createRequest(String uri) {
        return new HttpRequest("POST", uri, "HTTP 1.1");
    }
}
