package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.DataBase;
import exceptions.MethodNotAllowedException;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserController extends AbstractController{
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void service(Request request, Response response) {
        super.service(request,response);
    }

    @Override
    void doPost(Request request, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(request.getAllParameter(), User.class);
        DataBase.addUser(user);
        logger.info("UserInfo: {}, {}, {}, {}", user.getUserId(), user.getEmail(), user.getName(), user.getPassword());
        response.sendRedirect("/index.html");
    }

    @Override
    void doGet(Request request, Response response) {
        throw new MethodNotAllowedException();
    }
}
