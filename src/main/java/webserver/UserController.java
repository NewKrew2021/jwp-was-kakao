package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.UsersDto;
import service.UserService;
import utils.RequestParser;
import webserver.request.ContentType;
import webserver.request.Request;
import webserver.response.Response;

import java.io.IOException;

public class UserController {
    private static final String USER_CREATE_PATH = "/user/create";
    private static final String USER_LOGIN_PATH = "/user/login";
    private static final String USER_LIST_PATH = "/user/list";

    private UserService userService;
    private Handlebars handlebars;

    public UserController() {
        userService = new UserService();
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        handlebars = new Handlebars(loader);
    }

    public Response getResponse(Request request) {
        String path = request.getHeader().getPath().asUrl();
        String method = request.getHeader().getMethod();
        if (USER_CREATE_PATH.equals(path)) {
            if ("GET".equals(method)) {
                return addUserByGet(request);
            }
            if ("POST".equals(method)) {
                return addUserByPost(request);
            }
        }
        if (USER_LOGIN_PATH.equals(path)) {
            if ("POST".equals(method)) {
                return login(request);
            }
        }
        if (USER_LIST_PATH.equals(path)) {
            if ("GET".equals(method)) {
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
            return Response.redirectWithCookie(request, "logined=true", "/index.html");
        }
        return Response.redirectWithCookie(request, "logined=false", "/user/login_failed.html");
    }

    private Response showUsers(Request request) {
        if (request.getHeader().isLogined()) {
            return users();
        }
        return Response.redirect(request, "/user/login.html");
    }

    private Response users() {
        try {
            return Response.file(handlebars.compile("user/list").apply(new UsersDto(userService.findAll())).getBytes(), ContentType.HTML);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error();
        }
    }


}
