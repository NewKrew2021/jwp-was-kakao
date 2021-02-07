package controller;

import dto.UserDto;
import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

public class CreateUserController extends AbstractController {

    private static final String INDEX_PAGE = "/index.html";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.createUser(UserDto.from(httpRequest));
        httpResponse.sendNewPage(INDEX_PAGE, httpRequest.getSessionId());
    }

}
