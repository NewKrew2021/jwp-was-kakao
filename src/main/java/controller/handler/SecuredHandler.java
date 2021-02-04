package controller.handler;

import db.DataBase;
import db.Session;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpSession;

import java.io.IOException;
import java.util.UUID;

public class SecuredHandler implements Handler {
    private final Handler handler;

    public SecuredHandler(Handler handler) {
        this.handler = handler;
    }

    public HttpResponse handle(HttpRequest request) throws NoFileException, IOException {
        String cookieValue = request.getCookie("session");

        if (invalid(cookieValue)) {
            return new HttpResponse().redirect("/user/login.html");
        }

        return handler.handle(request);
    }

    private boolean invalid(String cookieValue) {
        if (cookieValue == null) {
            return true;
        }
        HttpSession session = Session.findSession(UUID.fromString(cookieValue));
        return session == null || DataBase.findUserById((String) session.getAttribute("userId")) == null;
    }
}
