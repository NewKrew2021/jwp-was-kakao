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
    private static final String PATH = "/user/list.html";

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        if (!UserService.isLoginedWithSession(httpRequest.getSession())) {
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
