package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class LoginControllerTest {
    @Mock
    UserService userService;

    LoginController loginController;

    HttpRequest request;
    HttpResponse response;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        loginController = new LoginController(userService);
        request = HttpRequest.empty();
        response = HttpResponse.ok(request);
    }

    @Test
    void successLogin() {
        when(userService.login("user", "password")).thenReturn(true);

        request.getHeader().addParam("userId", "user");
        request.getHeader().addParam("password", "password");
        loginController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("logined=true");
        assertThat(String.join("", response.getHeader().getHeaders())).contains("index.html");
    }

    @Test
    void failLogin() {
        when(userService.login("user", "password2")).thenReturn(false);

        request.getHeader().addParam("userId", "user");
        request.getHeader().addParam("password", "password2");

        loginController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("logined=false");
        assertThat(String.join("", response.getHeader().getHeaders())).contains("/user/login_failed.html");
    }
}