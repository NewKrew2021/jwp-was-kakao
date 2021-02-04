package controller;

import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import service.CreateUserService;

public class CreateUserController extends AbstractController {

    public static final String INDEX_HTML = "/index.html";
    private final CreateUserService createUserService = new CreateUserService();

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        createUserService.createUser(httpRequest);
        httpResponse
                .addRedirectionLocationHeader(INDEX_HTML)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        createUserService.createUser(httpRequest);
        httpResponse
                .addRedirectionLocationHeader(INDEX_HTML)
                .send(HttpResponseStatusCode.FOUND)
                .build();
    }

}
