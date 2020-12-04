package apps.slipp.controller;

import apps.slipp.service.SignUpService;
import webserver.http.*;
import webserver.http.view.RedirectView;

import java.util.List;

public class SignUpController implements Controller {

    private SignUpService signUpService = new SignUpService();

    @Override
    public ModelAndView execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<HttpRequestParam> params = HttpRequestParams.convertFrom(httpRequest.getBody());
        SignUp signUp = new SignUp(params);

        signUpService.signUp(signUp.toUserModel());

        return ModelAndView.of(new RedirectView("/index.html"));
    }


}
