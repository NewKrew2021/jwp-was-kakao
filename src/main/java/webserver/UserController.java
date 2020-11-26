package webserver;

import com.github.jknack.handlebars.Handlebars;
import model.UsersDto;
import service.UserService;
import utils.RequestParser;
import utils.TemplateUtils;
import webserver.request.ContentType;
import webserver.request.Request;
import webserver.response.Response;

import java.io.IOException;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static webserver.Router.USER_PATH;

public class UserController {
    private static final String USER_CREATE_PATH = USER_PATH + "/create";
    private static final String USER_LOGIN_PATH = USER_PATH + "/login";
    private static final String USER_LIST_PATH = USER_PATH + "/list";

    private UserService userService;
    private Handlebars handlebars;

    public UserController() {
        userService = new UserService();
        handlebars = TemplateUtils.getHandleBars();
    }

    public Response getResponse(Request request) {
        String path = request.getHeader().getPath().asUrl();
        String method = request.getHeader().getMethod();
        if (USER_CREATE_PATH.equals(path)) {
            if (GET.matches(method)) {
                return addUserByGet(request);
            }
            if (POST.matches(method)) {
                return addUserByPost(request);
            }
        }
        if (USER_LOGIN_PATH.equals(path)) {
            if (POST.matches(method)) {
                return login(request);
            }
        }
        if (USER_LIST_PATH.equals(path)) {
            if (GET.matches(method)) {
                return showUsers(request);
            }
        }
        return Response.notFound();
    }

    private Response addUserByPost(Request request) {
        userService.addUser(RequestParser.getRequestParamsFromBody(request.getBody()));
        return Response.redirect(request, "/index.html");
    }

    private Response addUserByGet(Request request) {
        userService.addUser(request.getHeader().getParams());
        return Response.redirect(request, "/index.html");
    }

    private Response login(Request request) {
        if (userService.login(RequestParser.getRequestParamsFromBody(request.getBody()))) {
            return Response.redirectWithCookie(request, Cookie.login(), "/index.html");
        }
        return Response.redirectWithCookie(request, Cookie.logout(), "/user/login_failed.html");
    }

    private Response showUsers(Request request) {
        if (isLogined(request)) {
            return users();
        }
        return Response.redirect(request, "/user/login.html");
    }

    private boolean isLogined(Request request) {
        return request.getHeader()
                .cookieContains(Cookie.login());
    }

    private Response users() {
        try {
            return Response.file(handlebars.compile("user/list")
                    .apply(new UsersDto(userService.findAll())).getBytes(), ContentType.HTML);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error();
        }
    }


}
