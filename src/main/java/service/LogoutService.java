package service;

import request.HttpRequest;
import session.Sessions;

public class LogoutService {
    public void logout(HttpRequest httpRequest) {
        Sessions.remove(httpRequest.getSessionId());
    }
}
