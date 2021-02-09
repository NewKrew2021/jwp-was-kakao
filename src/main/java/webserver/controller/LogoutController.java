package webserver.controller;

import db.HttpSessions;
import webserver.domain.*;

public class LogoutController extends AbstractController {
    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Cookies cookies = new Cookies(httpRequest.getHeader(HttpHeader.COOKIE));
        Cookie sessionCookie = cookies.getCookie("Session");
        if(sessionCookie != null) {
            HttpSessions.removeSession(sessionCookie.getValue());
        }
        return new HttpResponse.Builder()
                .status(HttpStatusCode.OK)
                .redirect("/index.html")
                .build();
    }
}
