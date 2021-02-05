package webserver.controller;

import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;
import webserver.exceptions.InvalidRequestException;

import java.io.IOException;

import static service.JwpService.getProfilePage;
import static service.JwpService.isLogin;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            httpResponse.send(HttpStatusCode.OK, getProfilePage());
        } catch (IOException e) {
            httpResponse.send(HttpStatusCode.INTERNAL_SERVER_ERROR, "읽어들이는데 문제가 발생하였습니다");
        }
    }

    @Override
    public void validateGetRequest(HttpRequest request, HttpResponse response) throws InvalidRequestException {
        if (!isLogin(request.getHeaders().get(HttpHeader.COOKIE))) {
            response.getHeaders().add(HttpHeader.LOCATION, "/user/login.html");
            response.send(HttpStatusCode.FOUND);
            throw new InvalidRequestException("잘못된 로그인 요청");
        }
    }
}
