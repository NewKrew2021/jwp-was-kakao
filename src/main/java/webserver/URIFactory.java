package webserver;

import dto.ParamValue;
import model.HttpStatus;
import model.User;
import model.factory.UserFactory;
import service.UserService;

public class URIFactory {

    private UserService userService = new UserService();

    public Response create(Request reqeust) {
        String uri = reqeust.getURLPath();

        if (uri.startsWith("/user/create")) {
            User user = UserFactory.create(reqeust.getParamMap());
            userService.create(user);
            return Response.of(HttpStatus.HTTP_FOUND, ParamValue.of("Location", "/index.html"));
        }

        return Response.of(HttpStatus.HTTP_OK);
    }
}
