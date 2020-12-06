package webserver.controller;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ControllerTest {
    @Test
    void userCreateController() {
        HttpRequest httpRequest = createRequest("/usr/create");
        httpRequest.setEntity(ImmutableMap.of("userId", "red"));
        assertThat(new CreateUserController().execute(httpRequest).getHeaders())
                .containsExactly(
                        "HTTP/1.1 302 Found ",
                        "Location: /index.html");
    }

    @Test
    void loginControllerWhenSuccess() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "1234"));
        assertThat(new LoginController().execute(httpRequest).getHeaders())
                .containsExactly(
                        "HTTP/1.1 302 Found ",
                        "Set-Cookie: logined=true; Path=/",
                        "Location: /index.html");
    }

    @Test
    void loginControllerWhenFailed() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "0000"));
        assertThat(new LoginController().execute(httpRequest).getHeaders())
                .containsExactly(
                        "HTTP/1.1 302 Found ",
                        "Set-Cookie: logined=false; Path=/",
                        "Location: /user/login_failed.html");
    }


    @Test
    void loginControllerWhenUserNotFound() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "notFound", "password", "0000"));
        assertThat(new LoginController().execute(httpRequest).getHeaders())
                .containsExactly(
                        "HTTP/1.1 302 Found ",
                        "Set-Cookie: logined=false; Path=/",
                        "Location: /user/login_failed.html");
    }

    @Test
    void userListControllerWhenLoggedIn() {
        HttpRequest httpRequest = createRequest("/user/list");
        httpRequest.addHeaders(ImmutableMap.of("Cookie", "logined=true"));
        Response response = new UserListController().execute(httpRequest);
        assertAll(() -> assertThat(response.getViewName()).isEqualTo("user/list"),
                  () -> assertThat(response.getModel()).isNotNull());
    }

    @Test
    void userListControllerWhenNotLoggedIn() {
        HttpRequest httpRequest = createRequest("/user/list");
        assertThat(new UserListController().execute(httpRequest).getHeaders())
                .containsExactly(
                        "HTTP/1.1 302 Found ",
                        "Location: /user/login.html");
    }

    @Test
    void staticContentController() {
        StaticContentController controller = new StaticContentController();
        assertAll(() -> {
                      Response response = controller.execute(createRequest("/css/bootstrap.min.css"));
                      assertThat(response.getHeaders()).containsExactly(
                              "HTTP/1.1 200 OK ",
                              "Content-Length: 109518",
                              "Content-Type: text/css");
                  },
                  () -> {
                      Response response = controller.execute(createRequest("/js/jquery-2.2.0.min.js"));
                      assertThat(response.getHeaders()).containsExactly(
                              "HTTP/1.1 200 OK ",
                              "Content-Length: 85589",
                              "Content-Type: application/js");
                  });
    }

    private HttpRequest createRequest(String uri) {
        return new HttpRequest("POST", uri, "HTTP 1.1");
    }

}
