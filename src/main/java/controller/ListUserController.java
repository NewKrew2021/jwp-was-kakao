package controller;

import dto.UserDTO;
import dto.UsersDTO;
import model.User;
import service.UserService;
import webserver.Request;
import webserver.Response;
import webserver.ResponseHandler;

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

    public UsersDTO getUsersDTO(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(UserDTO.of(user)));
        return new UsersDTO(userDTOS);
    }
}
