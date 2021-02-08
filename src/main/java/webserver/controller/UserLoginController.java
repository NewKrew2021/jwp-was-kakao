package webserver.controller;

import session.controller.SessionController;
import session.model.Session;
import user.controller.UserController;
import user.model.User;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.model.*;

import java.util.Objects;

public class UserLoginController implements Controller {
    private static final String USERID = "userId";
    private static final String PASSWORD = "password";

    private static final String path = "/user/login";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String id = httpRequest.getParameter(USERID);
        String password = httpRequest.getParameter(PASSWORD);
        User user = UserController.findUserById(id);

        HttpResponse httpResponse = new HttpResponse();

        String location = FileIoUtils.LOGIN_FAIL_PATH;

        if (!Objects.isNull(user) && user.getPassword().equals(password)) {
            location = RequestHandler.BASE_URL;
            Session session = SessionController.createSession();
            httpResponse.addCookie(Cookie.SESSION, session.getId());
        }

        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.LOCATION, location);

        return httpResponse;
    }
}
