package controller;

import annotation.web.RequestMethod;
import db.DataBase;
import model.User;
import utils.HttpUtils;
import webserver.Controller;
import webserver.Model;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

import java.util.Map;

public class UserLoginController extends Controller {
    public UserLoginController() {
        super(RequestMethod.POST, "/user/login");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public String handleRequest(HttpRequest request, HttpResponse httpResponse, Model model) {
        Map<String, String> params = HttpUtils.getParamMap(request.getBody());

        User user = DataBase.findUserById(params.get("userId"));
        if (user != null && user.getPassword().equals(params.get("password"))) {
            HttpSession session = HttpSession.getNewSession();
            session.setAttribute(HttpSession.USER, user);
            httpResponse.addCookie(HttpSession.SESSION, session.getId());
            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }
}
