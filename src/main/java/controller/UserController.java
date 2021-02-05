package controller;

import db.DataBase;
import http.Body;
import http.Cookie;
import http.Cookies;
import http.HttpResponse;
import model.Session;
import model.User;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    private static final String INDEX_HTML = "/index.html";
    private static final String USER_FORM_HTML = "/user/form.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";
    private static final String USER_LOGIN_HTML = "/user/login.html";
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html;charset=utf-8";
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    public static final String USER = "user";
    public static final String SESSION = "session";
    public static final String USERS = "users";

    public static Handler createUserHandler = (request) -> {
        try {
            Body body = request.getBody();

            User user = new User(body.get(USER_ID),
                    body.get(PASSWORD),
                    body.get(NAME),
                    body.get(EMAIL));
            DataBase.addUser(user);

            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(INDEX_HTML)
                    .build();
        } catch (IllegalArgumentException e) {
            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(USER_FORM_HTML)
                    .build();
        }
    };

    public static Handler loginUserHandler = (request) -> {
        try {
            Body body = request.getBody();
            User user = DataBase.findUserById(body.get(USER_ID));
            user.verifyPassword(body.get(PASSWORD));

            Session session = DataBase.addSession();
            session.setAttribute(USER, user);

            Cookie cookie = new Cookie(SESSION, session.getId());
            cookie.setPath("/");

            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(INDEX_HTML)
                    .cookie(cookie)
                    .build();
        } catch (NullPointerException | IllegalArgumentException e) {
            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(LOGIN_FAILED_HTML)
                    .build();
        }
    };

    public static Handler listUserHandler = (request) -> {
        try {
            Cookies cookies = request.getCookies();
            Cookie sessionId = cookies.getCookie(SESSION);

            Session session = DataBase.findSessionById(sessionId.getValue());
            session.getAttribute(USER);

            Map<String, Object> params = new HashMap<>();
            params.put(USERS, DataBase.findAll());

            return new HttpResponse.Builder()
                    .status(HttpStatus.OK)
                    .contentType(TEXT_HTML_CHARSET_UTF_8)
                    .body(request.getUri(), params)
                    .build();
        } catch (NullPointerException e) {
            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(USER_LOGIN_HTML)
                    .build();
        }
    };

    public static Handler logoutUserHandler = (request) -> {
        try {
            Cookies cookies = request.getCookies();
            Cookie sessionId = cookies.getCookie(SESSION);

            Session session = DataBase.findSessionById(sessionId.getValue());
            session.invalidate();

            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(INDEX_HTML)
                    .build();
        } catch (NullPointerException e) {
            return new HttpResponse.Builder()
                    .status(HttpStatus.FOUND)
                    .redirect(USER_LOGIN_HTML)
                    .build();
        }
    };
}
