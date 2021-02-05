package service;

import db.DataBase;
import dto.UserDto;
import model.User;
import request.HttpSession;
import webserver.SessionHandler;

import java.util.Collection;
import java.util.Optional;

public class UserService {
    public static final String LOGINED = "logined";
    public static final String TRUE = "true";

    public static void createUser(UserDto userDto) {
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        DataBase.addUser(user);
    }

    public static Collection<User> findAllUser() {
        return DataBase.findAll();
    }

    public static boolean isLoginSuccess(String userId, String password) {
        Optional<User> user = DataBase.findUserById(userId);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public static void loginUser(String sessionId) {
        HttpSession httpSession = SessionHandler.getSession(sessionId).get();
        httpSession.setAttribute(LOGINED, TRUE);
    }

    public static void loginOutUser(String sessionId) {
        HttpSession httpSession = SessionHandler.getSession(sessionId).get();
        httpSession.invalidate();
    }

    public static boolean isLogined(String sessionId) {
        HttpSession httpSession = SessionHandler.getSession(sessionId).get();
        Optional<Object> loginValue = httpSession.getAttribute(LOGINED);
        return loginValue.isPresent() && loginValue.get().toString().equals(TRUE);
    }
}
