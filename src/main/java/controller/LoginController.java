package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.DataBase;
import exceptions.MethodNotAllowedException;
import model.Request;
import model.Response;
import model.User;

import java.util.Optional;

public class LoginController extends AbstractController {
    @Override
    public void service(Request request, Response response) {
        super.service(request, response);
    }

    @Override
    void doPost(Request request, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        User loginUser = mapper.convertValue(request.getAllParameter(), User.class);
        User user = Optional.ofNullable(DataBase.findUserById(loginUser.getUserId()))
                .orElseThrow(NullPointerException::new);
        if (user.getPassword().equals(loginUser.getPassword())) {
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
