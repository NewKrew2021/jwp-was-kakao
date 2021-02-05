package controller;

import exceptions.MethodNotAllowedException;
import model.Parameter;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class CreateUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void service(Request request, Response response) {
        super.service(request, response);
    }

    @Override
    void doPost(Request request, Response response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email")
        );
        logger.debug("UserInfo : {}, {}", user.getUserId(), user.getPassword());
        if(UserService.save(user).isPresent()){
            response.sendRedirect("/index.html");
        }
        logger.debug("가입에 성공하지 못한 경우");
        response.sendRedirect("/user/form.html");
    }

    @Override
    void doGet(Request request, Response response) {
        throw new MethodNotAllowedException();
    }
}
