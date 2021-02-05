package controller;

import exceptions.MethodNotAllowedException;
import model.Request;
import model.Response;
import model.User;
import service.UserService;

import java.util.Optional;

public class LoginController extends AbstractController {
    @Override
    public void service(Request request, Response response) {
        super.service(request, response);
    }

    @Override
    void doPost(Request request, Response response) {
        User loginUser = User.of(request.getParameter("userId"), request.getParameter("password"));
        Optional<User> user = UserService.findByUserId(loginUser.getUserId());
        if (user.isPresent() && user.get().isSamePassword(loginUser.getPassword())) {
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }
        response.addHeader("Set-Cookie", "logined=false;");
        response.sendRedirect("/user/login_faild.html");
    }

    @Override
    void doGet(Request request, Response response) {
        throw new MethodNotAllowedException();
    }
}
