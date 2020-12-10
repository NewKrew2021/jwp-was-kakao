package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.Users;
import service.UserService;
import webserver.config.ServerConfigConstants;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserController implements HttpController {

    public static final String INDEX_HTML = "/index.html";
    public static final String FORM_HTML = "/form.html";
    public static final String USER_CREATE = "/user/create";
    public static final String USER_LOGIN_HTML = "/user/login.html";
    public static final String USER_LOGIN_FAILED_HTML = "/user/login_failed.html";
    public static final String LOGIN_FAILED_HTML = "/login_failed.html";
    public static final String USER_LIST = "/user/list";
    public static final String USER_LIST_HTML = "/user/list.html";
    public static final String LOGIN = "/user/login";

    UserService userService;

    public UserController() {
         userService = new UserService();
    }

    @Override
    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        if(httpRequest.getPath().endsWith(FORM_HTML)){
            return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + httpRequest.getPath());
        }else if(httpRequest.getPath().endsWith(USER_CREATE)){
            if(httpRequest.getMethod().equals(HttpMethod.POST)){
                RequestBody requestBody = httpRequest.getBody();
                String userId = requestBody.getParameter("userId");
                String password = requestBody.getParameter("password");
                String name = requestBody.getParameter("name");
                String email = requestBody.getParameter("email");
                User user = new User(userId, password, name, email);
                userService.addUser(user);
            }
            return HttpResponse.redirect(responseHeader, INDEX_HTML);
        }else if(httpRequest.getPath().endsWith(LOGIN)){
            RequestBody requestBody = httpRequest.getBody();
            String userId = requestBody.getParameter("userId");
            String password = requestBody.getParameter("password");
            if(userService.isValidUserByLogin(userId, password)){
                responseHeader.setLoginCookie(httpRequest, true);
                return HttpResponse.redirect(responseHeader, INDEX_HTML);
            }else{
                responseHeader.setLoginCookie(httpRequest, false);
                return HttpResponse.redirect(responseHeader, USER_LOGIN_FAILED_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LIST_HTML)){
            if(httpRequest.isLogin()){
                Users users = userService.findAll();
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix(ServerConfigConstants.TEMPLATES_LOADER_PATH_PREFIX);
                loader.setSuffix(ResponseHeader.HTML);
                Handlebars handlebars = new Handlebars(loader);
                return HttpResponse.from(handlebars.compile(USER_LIST).apply(users).getBytes());
            }else{
                return HttpResponse.redirect(responseHeader, USER_LOGIN_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LOGIN_HTML)){
            return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + USER_LOGIN_HTML);
        }else if(httpRequest.getPath().endsWith(LOGIN_FAILED_HTML)){
            return HttpResponse.from(ResponseStatus.OK, responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + USER_LOGIN_FAILED_HTML);
        }else {
            return HttpResponse.error();
        }
    }

}
