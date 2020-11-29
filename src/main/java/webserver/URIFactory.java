package webserver;

import model.User;
import model.factory.UserFactory;
import service.UserService;

public class URIFactory {

    private UserService userService = new UserService();

    public void create(Request reqeust) {
        String uri = reqeust.getURLPath();

        if (uri.startsWith("/user/create")) {
            User user = UserFactory.create(reqeust.getParamMap());
            userService.create(user);
        }
    }
}
