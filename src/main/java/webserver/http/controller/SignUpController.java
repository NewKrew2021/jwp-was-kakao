package webserver.http.controller;

import db.DataBase;
import model.User;
import webserver.http.*;

import java.util.List;

public class SignUpController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        SignUp signUp = new SignUp(params);

        User newUser = new User(
                signUp.getUserId(),
                signUp.getPassword(),
                signUp.getName(),
                signUp.getEmail()
        );
        DataBase.addUser(newUser);

        httpResponse.setStatus(HttpStatus.x302_Found);
        httpResponse.addHeader("Location", "/index.html");
    }


}
