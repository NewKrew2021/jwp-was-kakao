package user.ui;

import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.ui.AbstractController;

public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        UserService.insert(new UserCreateValue(httpRequest));
        httpResponse.sendRedirect("/index.html");
    }
}
