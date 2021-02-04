package webserver.controller;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import webserver.RequestHandler;
import webserver.model.*;

public class UserLoginController implements Controller {

    private static final String path = "/user/login";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        String id = httpRequest.getParameter(Parameter.USERID);
        String password = httpRequest.getParameter(Parameter.PASSWORD);
        User user = DataBase.findUserById(id);

        String logined = Cookie.LOGINED_TRUE;
        String location = RequestHandler.BASE_URL;

        if (user == null || !user.getPassword().equals(password)) {
            logined = Cookie.LOGINED_FALSE;
            location = FileIoUtils.LOGIN_FAIL_PATH;
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.LOCATION, location);
        httpResponse.addCookie(Cookie.LOGINED, logined);

        return httpResponse;
    }
}
