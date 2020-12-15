package controller;

import model.Model;
import model.user.User;
import model.user.UsersDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.request.RequestHeader;
import webserver.response.HttpResponse;

import java.util.Collections;

import static controller.UserController.LOGIN_COOKIE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static utils.template.TemplateUtils.TEMPLATE_PREFIX;

class UserControllerTest {
    @Mock
    UserService userService;

    UserController userController;

    HttpRequest request;
    HttpResponse response;
    Model model = Model.empty();

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
        request = HttpRequest.empty();
        response = HttpResponse.ok(request);
    }

    @Test
    void addUser() {
        when(userService.addUser(any())).thenReturn(User.nobody());

        userController.addUser(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("/index.html");
    }

    @Test
    void showAll() {
        when(userService.findAll()).thenReturn(Collections.singletonList(User.nobody()));
        request.getHeader().addHeader(RequestHeader.COOKIE, Collections.singletonList(new Cookie(LOGIN_COOKIE_NAME, "true", null)));

        String viewPath = userController.showUsers(request, response, model);

        assertThat(viewPath).isEqualTo(TEMPLATE_PREFIX + "/user/list.html");
        assertThat(((UsersDto) model.getData().get("usersDto")).getUsers().size()).isEqualTo(1);
    }

    @Test
    void cannotShowAllBeforeLogin() {
        request.getHeader().addHeader(RequestHeader.COOKIE, Collections.singletonList(new Cookie(LOGIN_COOKIE_NAME, "false", null)));

        String viewPath = userController.showUsers(request, response, model);

        assertThat(viewPath).isNull();
        assertThat(String.join("", response.getHeader().getHeaders())).contains("/user/login.html");

    }


    @Test
    void successLogin() {
        when(userService.login("user", "password")).thenReturn(true);

        request.getHeader().addParameter("userId", "user");
        request.getHeader().addParameter("password", "password");
        userController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("logined=true");
        assertThat(String.join("", response.getHeader().getHeaders())).contains("index.html");
    }

    @Test
    void failLogin() {
        when(userService.login("user", "password2")).thenReturn(false);

        request.getHeader().addParameter("userId", "user");
        request.getHeader().addParameter("password", "password2");

        userController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("logined=false");
        assertThat(String.join("", response.getHeader().getHeaders())).contains("/user/login_failed.html");
    }
}