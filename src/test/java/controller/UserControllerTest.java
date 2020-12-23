package controller;

import model.Model;
import model.user.User;
import model.user.UsersDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.UserService;
import webserver.request.HttpRequest;
import webserver.request.HttpSession;
import webserver.response.HttpResponse;

import java.util.Collections;
import java.util.HashMap;

import static controller.UserController.LOGIN_ATTRIBUTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        request.getHeader().setParameters(new HashMap<String, String>() {{
            put("userId", "1");
            put("password", "1");
            put("name", "1");
            put("email", "a@a.com");
        }});

        userController.addUser(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("/index.html");
    }

    @Test
    void showAll() {
        when(userService.findAll()).thenReturn(Collections.singletonList(User.nobody()));
        HttpSession session = HttpSession.of("1");
        session.setAttribute(LOGIN_ATTRIBUTE, true);
        request.setSession(session);

        String viewPath = userController.showUsers(request, response, model);

        assertThat(viewPath).isEqualTo("/user/list.html");
        assertThat(((UsersDto) model.getData().get("usersDto")).getUsers().size()).isEqualTo(1);
    }

    @Test
    void cannotShowAllBeforeLogin() {
        HttpSession session = HttpSession.of("1");
        request.setSession(session);
        String viewPath = userController.showUsers(request, response, model);

        assertThat(viewPath).isNull();
        assertThat(String.join("", response.getHeader().getHeaders())).contains("/user/login.html");

    }


    @Test
    void successLogin() {
        when(userService.login("user", "password")).thenReturn(true);

        request.getHeader().addParameter("userId", "user");
        request.getHeader().addParameter("password", "password");
        HttpSession session = HttpSession.of("1");
        request.setSession(session);
        userController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("index.html");
        assertThat(session.getAttribute(LOGIN_ATTRIBUTE)).isEqualTo(true);
    }

    @Test
    void failLogin() {
        when(userService.login("user", "password2")).thenReturn(false);

        request.getHeader().addParameter("userId", "user");
        request.getHeader().addParameter("password", "password2");
        HttpSession session = HttpSession.of("1");
        request.setSession(session);
        userController.login(request, response);

        assertThat(String.join("", response.getHeader().getHeaders())).contains("/user/login_failed.html");
        assertThat(session.getAttribute(LOGIN_ATTRIBUTE)).isEqualTo(false);
    }
}