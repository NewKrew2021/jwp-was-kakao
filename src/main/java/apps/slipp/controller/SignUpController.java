package apps.slipp.controller;

import apps.slipp.service.SignUpService;
import db.DataBase;
import model.User;
import webserver.http.*;

import java.util.List;

public class SignUpController implements Controller {

    private SignUpService signUpService = new SignUpService();

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        SignUp signUp = new SignUp(params);

        signUpService.signUp(signUp.toUserModel());

        httpResponse.setStatus(HttpStatus.x302_Found);
        httpResponse.addHeader("Location", "/index.html");
    }


}
