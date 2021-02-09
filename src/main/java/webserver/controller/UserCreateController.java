package webserver.controller;

import user.controller.UserController;
import user.model.User;
import webserver.RequestHandler;
import webserver.model.*;

public class UserCreateController implements Controller {
    private static final String USERID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final String path = "/user/create";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        User user = new User(
                httpRequest.getParameter(USERID),
                httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME),
                httpRequest.getParameter(EMAIL)
        );
        UserController.addUser(user);

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.LOCATION, RequestHandler.BASE_URL);

        return httpResponse;
    }
}
