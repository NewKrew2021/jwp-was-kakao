package handler;

import db.DataBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.*;
import webserver.HttpServlet;
import webserver.SessionManager;

import java.util.Map;

public class UserLoginHandler implements HttpServlet {
    private final SessionManager sessionManager;

    public UserLoginHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());

        return DataBase.findUserById(parameters.get("userId"))
                .filter(user -> user.isSamePassword(parameters.get("password")))
                .map(user -> {
                    HttpSession httpSession = sessionManager.create();
                    return getResponse("/index.html", sessionManager.getSessionCookie(httpSession) + "; Path=/");
                })
                .orElseGet(() -> getResponse("/user/login_failed.html", "Path=/"));
    }

    private HttpResponse getResponse(String location, String cookie) {
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeaders.LOCATION, location);
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, cookie);
        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST);
    }
}
