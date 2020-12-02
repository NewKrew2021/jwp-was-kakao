package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.HandleBarUtils;
import webserver.http.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends Controller {
    public static final String PATH = "/user/list.html";

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        Map<String, String> cookie = new HashMap<>();
        httpRequest.getCookiesInMap(cookie);

        if (!UserService.isLogined(cookie)) {
            return redirectToLoginPage();
        }

        byte[] body = renderBodyWithUserList();
        return new HttpResponse(HttpCode._200, body);
    }


    private HttpResponse redirectToLoginPage() {
        return new HttpResponseBuilder()
                .with302Redirect("/user/login.html")
                .build();
    }

    private byte[] renderBodyWithUserList() {
        Collection<User> users = UserService.getAllUsers();
        Map<String, Collection<User>> context = new HashMap<>();
        context.put("context", users);

        return HandleBarUtils.renderTemplate("user/list", context);
    }
}
