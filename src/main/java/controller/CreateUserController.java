package controller;

import request.HttpRequest;
import response.HttpResponse;
import service.Service;

public class CreateUserController extends AbstractController {

    private static final String INDEX_PAGE = "/index.html";

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        Service.createUser(httpRequest.getParameter(USER_ID), httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME), httpRequest.getParameter(EMAIL));
        httpResponse.response302Header(INDEX_PAGE);
    }

}
