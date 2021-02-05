package webserver.controller;

import session.controller.SessionController;
import utils.FileIoUtils;
import webserver.model.*;

public class UserLogoutController implements Controller {

    private static final String path = "/user/logout";

    public String getPath() {
        return path;
    }

    @Override
    public HttpResponse service(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse();

        String location = FileIoUtils.INDEX_PATH;

        String sessionId = httpRequest.getCookie(Cookie.SESSION);
        SessionController.invalidateSession(sessionId);

        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.addHeader(HttpHeader.LOCATION, location);

        return httpResponse;
    }
}
