package controller;

import dto.ParamValue;
import model.factory.UserFactory;
import service.UserService;
import webserver.Request;
import webserver.Response;

import java.util.Optional;

public class LoginUserController extends AbstractController {

    private UserService userService = new UserService();

    @Override
    public void doPost(Request request, Response response) {
        Optional<ParamValue> optionalParamValue = request.getParamMap();

        if (optionalParamValue.isPresent()) {
            ParamValue paramMap = optionalParamValue.get();
            String userId = UserFactory.parserUserId(paramMap);
            String password = UserFactory.parserUserPassword(paramMap);

            boolean isLogin = userService.isLogin(userId, password);
            response.setHeaderCookie(isLogin);

            if (isLogin) {
                response.sendRedirect("/index.html");
                return;
            }
        }
        response.sendRedirect("/user/login_failed.html");
    }
}
