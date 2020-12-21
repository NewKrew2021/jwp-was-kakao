package controller;

import dto.ParamValue;
import model.User;
import model.factory.UserFactory;
import service.UserService;
import utils.MessageUtils;
import webserver.Request;
import webserver.Response;

import java.awt.geom.IllegalPathStateException;
import java.util.Optional;

public class CreateUserController extends AbstractController {

    private UserService userService = new UserService();

    @Override
    public void doGet(Request request, Response response) {
        throw new IllegalPathStateException(MessageUtils.ILLEGAL_PATH_STATE);
    }

    @Override
    public void doPost(Request request, Response response) {
        Optional<ParamValue> optionalParamValue = request.getParamMap();
        if (!optionalParamValue.isPresent()) {
            throw new IllegalStateException(MessageUtils.PARAM_VALUE_IS_EMPTY);
        }

        User user = UserFactory.create(optionalParamValue.get());
        userService.create(user);

        response.sendRedirect("/index.html");
    }
}
