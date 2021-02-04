package controller;

import db.DataBase;
import exceptions.TemplateApplyFailException;
import request.HttpRequest;
import response.HttpResponse;

import java.io.IOException;


public class ListUserController extends AbstractController {

    private static final String MAIN_PAGE = "/user/login.html";
    private static final String FALSE = "false";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            try {
                httpResponse.responseTemplate(DataBase.findAll());
            } catch (IOException e) {
                e.printStackTrace();
                throw new TemplateApplyFailException();
            }
            return;
        }
        httpResponse.response302Header(MAIN_PAGE, FALSE);
    }

}
