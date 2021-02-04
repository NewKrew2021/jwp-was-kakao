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
            response.addHeader("Set-Cookie", "logined=true; Path=/; " + session.getId());
//            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("http://" + request.getHost() + "/index.html");

        }

        response.addHeader("Set-Cookie", "logined=false; Path=/");
        response.sendRedirect("http://" + request.getHost() + "/user/login_failed.html");

    }
}
