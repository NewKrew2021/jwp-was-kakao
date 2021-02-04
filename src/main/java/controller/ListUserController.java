package controller;

import db.DataBase;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import utils.TemplateUtils;
import java.io.IOException;

public class ListUserController extends AbstractController {

    private static final String LOGIN_PAGE = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            try {
                httpResponse
                        .addResponseBody(TemplateUtils.makeListUserTemplate(DataBase.findAll()))
                        .send(HttpResponseStatusCode.OK)
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        httpResponse
                .addRedirectionLocationHeader(LOGIN_PAGE)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
