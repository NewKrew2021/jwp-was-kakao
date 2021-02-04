package controller;

import exception.NotDefinedMethodException;
import webserver.Request;
import webserver.Response;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(Request request, Response response) throws Exception {
        if (request.getHeader("Cookie") != null &&
                request.getHeader("Cookie").contains("logined=true")) {
            response.userListForward(request.getUri());
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
        throw new NotDefinedMethodException();
    }
}
