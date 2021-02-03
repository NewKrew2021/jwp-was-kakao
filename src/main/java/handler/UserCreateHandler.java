package handler;

import db.DataBase;
import model.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import web.*;
import webserver.HttpServlet;

import java.util.Map;

public class UserCreateHandler implements HttpServlet {
    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        Map<String, String> parameters = HttpUrl.parseParameter(BodyMapper.read(httpRequest.getHttpBody().getBody(), String.class));
        DataBase.addUser(new User(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")));

        HttpResponse httpResponse = HttpResponse.of(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeaders.LOCATION, "/index.html");
        return httpResponse;
    }

    @Override
    public boolean isSupport(HttpRequest httpRequest) {
        HttpUrl httpUrl = httpRequest.getHttpUrl();
        return httpUrl.hasSameUrl("/user/create") && httpRequest.hasSameMethod(HttpMethod.POST);
    }
}
