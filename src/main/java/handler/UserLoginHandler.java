package handler;

import db.DataBase;
import model.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.HttpHeaders;
import web.HttpRequest;
import web.HttpResponse;
import web.HttpUrl;
import webserver.HttpServlet;

import java.util.Map;
import java.util.Optional;

public class UserLoginHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Map<String, String> parameters = HttpUrl.parseParameter(httpRequest.getHttpBody().getBody());
        Optional<User> user = DataBase.findUserById(parameters.get("userId"));

        if (!user.isPresent() || !user.get().getPassword().equals(parameters.get("password"))) {
            HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
            httpResponse.addHeader(HttpHeaders.LOCATION, "/user/login_failed.html");
            httpResponse.addHeader(HttpHeaders.SET_COOKIE, "logined=false; Path=/");

            return httpResponse;
        }

        HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeaders.LOCATION, "/index.html");
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, "logined=true; Path=/");

        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/login") && httpRequest.hasSameMethod(HttpMethod.POST);
    }
}
