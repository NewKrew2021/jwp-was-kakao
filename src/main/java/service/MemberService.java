package service;

import db.DataBase;
import domain.HttpRequest;
import model.User;

public class MemberService {

    public static final String DUPLICATED_USER_MESSAGE = "이미 존재하는 id 입니다.";

    public void addUser(HttpRequest httpRequest) {
        String requestedUserId = httpRequest.getParameter("userId");
        if (DataBase.findUserById(requestedUserId) != null) {
            throw new IllegalArgumentException(DUPLICATED_USER_MESSAGE);
        }
        DataBase.addUser(new User(httpRequest.getParameters()));
    }

    public boolean isInvalidMemberRequest(HttpRequest httpRequest) {
        String userId = httpRequest.getParameter("userId");
        String password = httpRequest.getParameter("password");
        return userId.isEmpty() || password.isEmpty();
    }

    public boolean isExist(HttpRequest httpRequest) {
        return !isInvalidMemberRequest(httpRequest) &&
                DataBase.findUserById(httpRequest.getParameter("userId")) != null;
    }

}
