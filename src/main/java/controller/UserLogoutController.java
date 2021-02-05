package controller;

import annotation.web.RequestMethod;
import db.DataBase;

import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public class UserLogoutController extends Controller {
    public UserLogoutController() {
        super(RequestMethod.GET, "/user/logout");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse response, Model model) {
        HttpSession session = DataBase.findSessionById(request.getRequestHeaders().getCookie(HttpSession.SESSION));
        if (session != null) {
            session.invalidate();
            return "redirect:/index.html";
        }
        return "redirect:/user/login.html";
    }
}
