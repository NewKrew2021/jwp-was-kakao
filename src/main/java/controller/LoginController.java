package controller;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;
import vo.UserSessionVO;
import webserver.HttpServletRequest;
import webserver.HttpSession;

public class LoginController extends AbstractController{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams().get("userId"));
        if (user != null && user.getPassword().equals(request.getParams().get("password"))) {
            UserSessionVO userSessionVO = new UserSessionVO(user);
            HttpSession session = HttpServletRequest.getSession();
            session.setAttribute("USER", userSessionVO);
            response.setCookie("true", "/", session.getId());
            response.sendRedirect("http://" + request.getHost() + "/index.html");

        }
        response.setCookie("false", "/", null);
        response.sendRedirect("http://" + request.getHost() + "/user/login_failed.html");

    }
}
