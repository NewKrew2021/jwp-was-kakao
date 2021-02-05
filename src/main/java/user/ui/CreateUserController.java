package user.ui;

import user.service.UserService;
import user.vo.UserCreateValue;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.SessionStorage;
import webserver.ui.AbstractController;

public class CreateUserController extends AbstractController {

    private static final String INDEX_HTML = "/index.html";

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse, SessionStorage sessionStorage) {
        UserService.insert(new UserCreateValue(httpRequest));
        httpResponse.sendRedirect(INDEX_HTML);
    }
}
