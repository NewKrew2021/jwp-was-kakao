package controller;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import service.ListUserService;
import utils.TemplateUtils;
import java.io.IOException;

public class ListUserController extends AbstractController {

    private final ListUserService listUserService = new ListUserService();
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);
    private static final String LOGIN_PAGE = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            try {
                httpResponse
                        .addResponseBody(listUserService.makeTemplateBodyByUser())
                        .send(HttpResponseStatusCode.OK)
                        .build();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return;
        }
        httpResponse
                .addRedirectionLocationHeader(LOGIN_PAGE)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
