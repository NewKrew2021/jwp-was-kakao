package controller;

import dto.UserDTO;
import dto.UsersDTO;
import model.User;
import service.UserService;
import utils.MessageUtils;
import webserver.Request;
import webserver.Response;
import webserver.ResponseHandler;

import java.awt.geom.IllegalPathStateException;
import java.util.ArrayList;
import java.util.List;

public class ListUserController extends AbstractController {

    private UserService userService = new UserService();

    @Override
    public void doGet(Request request, Response response) {
        if (request.isLogined()) {
            List<User> users = userService.getList();
            UsersDTO usersDTO = getUsersDTO(users);

            String view = ResponseHandler.createTemplatesView(request.getPathGateway(), usersDTO);
            response.response200Body(view);
            return;
        }
        response.sendRedirect("/user/login.html");
    }

    @Override
    public void doPost(Request request, Response response) {
        throw new IllegalPathStateException(MessageUtils.ILLEGAL_PATH_STATE);
    }

    public UsersDTO getUsersDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(UserDTO.of(user)));
        return new UsersDTO(userDTOS);
    }
}
