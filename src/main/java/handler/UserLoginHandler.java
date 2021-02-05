package handler;

import db.DataBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.*;
import webserver.HttpServlet;

import java.util.Map;

import static web.HttpSession.SESSION_ID;

public class UserLoginHandler implements HttpServlet {

    public static final String LOGIN = "login";

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Map<String, String> parameters = HttpUrl.parseParameter(BodyMapper.read(httpRequest.getHttpBody().getBody(), String.class));

        return DataBase.findUserById(parameters.get("userId"))
                .filter(user -> user.isSamePassword(parameters.get("password")))
                .map(user -> getResponse("/index.html", true, httpRequest.getHttpSession()))
                .orElseGet(() -> getResponse("/user/login_failed.html", false, httpRequest.getHttpSession()));
    }

    private HttpResponse getResponse(String location, boolean isLogined, HttpSession session) {
        session.setAttribute(LOGIN, isLogined);
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeaders.LOCATION, location);
        httpResponse.setCookie(HttpCookies.of(SESSION_ID, session.getId()));
        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST);
    }
}
