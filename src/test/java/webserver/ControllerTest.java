package webserver;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ControllerTest {
    @Test
    void userCreateController() {
        HttpRequest httpRequest = createRequest("/usr/create");
        httpRequest.setEntity(ImmutableMap.of("userId", "red"));
        assertThat(new CreateUserController().execute(httpRequest).getHeaders())
                .containsExactly("Location: /index.html");
    }

    @Test
    void loginControllerWhenSuccess() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "1234"));
        assertThat(new LoginController().execute(httpRequest).getHeaders())
                .containsExactly("Set-Cookie: logined=true; Path=/", "Location: /index.html");
    }

    @Test
    void loginControllerWhenFailed() {
        HttpRequest httpRequest = createRequest("/user/login");
        httpRequest.setEntity(ImmutableMap.of("userId", "blue", "password", "0000"));
        assertThat(new LoginController().execute(httpRequest).getHeaders())
                .containsExactly("Set-Cookie: logined=false; Path=/", "Location: /user/login_failed.html");
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
                .containsExactly("Location: /user/login.html");
    }

    @Test
    void staticContentController() {
        StaticContentController controller = new StaticContentController();
        assertThat(controller.execute(createRequest("/css/bootstrap.min.css"))
                           .getHeaders())
                .containsExactly("Content-Type: text/css");
    }

    private HttpRequest createRequest(String uri) {
        return new HttpRequest("POST", uri, "HTTP 1.1");
    }
}
