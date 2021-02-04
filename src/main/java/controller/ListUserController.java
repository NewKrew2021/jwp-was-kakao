package controller;

import exceptions.TemplateApplyFailException;
import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

import java.io.IOException;


public class ListUserController extends AbstractController {

    private static final String LOGIN_PAGE = "/user/login.html";
    private static final String FALSE = "false";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            try {
                httpResponse.responseTemplate(UserService.findAllUser());
            } catch (IOException e) {
                e.printStackTrace();
                throw new TemplateApplyFailException();
            }
            return;
        }
        httpResponse.response302Header(LOGIN_PAGE, FALSE);
    }

}
