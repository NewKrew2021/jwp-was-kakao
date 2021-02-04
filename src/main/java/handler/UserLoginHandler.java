package handler;

import db.DataBase;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.*;
import webserver.HttpServlet;

import java.util.Map;

public class UserLoginHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Map<String, String> parameters = HttpUrl.parseParameter(BodyMapper.read(httpRequest.getHttpBody().getBody(), String.class));

        return DataBase.findUserById(parameters.get("userId"))
                .filter(user -> user.isSamePassword(parameters.get("password")))
                .map(user -> getResponse("/index.html", HttpCookies.logined()))
                .orElseGet(() -> getResponse("/user/login_failed.html", HttpCookies.notLogined()));
    }

    private HttpResponse getResponse(String location, HttpCookies cookie) {
        HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeaders.LOCATION, location);
        httpResponse.setCookie(cookie);
        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST);
    }
}
