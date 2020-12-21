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

import static webserver.config.ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX;

public class UserController extends HttpAbstractController{

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
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = ResponseHeader.of(httpRequest);
        if(httpRequest.getPath().endsWith(FORM_HTML)){
            httpResponse.forward(responseHeader, ServerConfigConstants.TEMPLATES_RESOURCE_PATH_PREFIX + httpRequest.getPath());
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
            httpResponse.sendRedirect(responseHeader, INDEX_HTML);
        }else if(httpRequest.getPath().endsWith(LOGIN)){
            RequestBody requestBody = httpRequest.getBody();
            String userId = requestBody.getParameter("userId");
            String password = requestBody.getParameter("password");
            if(userService.isValidUserByLogin(userId, password)){
                responseHeader.setLoginCookie(httpRequest, true);
                httpResponse.sendRedirect(responseHeader, INDEX_HTML);
            }else{
                responseHeader.setLoginCookie(httpRequest, false);
                httpResponse.sendRedirect(responseHeader, USER_LOGIN_FAILED_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LIST_HTML) || httpRequest.getPath().endsWith(USER_LIST)){
            if(httpRequest.isLogin()){
                Users users = userService.findAll();
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix(ServerConfigConstants.TEMPLATES_LOADER_PATH_PREFIX);
                loader.setSuffix(ResponseHeader.HTML);
                Handlebars handlebars = new Handlebars(loader);
                httpResponse.forward(handlebars.compile(USER_LIST).apply(users).getBytes());
            }else{
                httpResponse.sendRedirect(responseHeader, USER_LOGIN_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LOGIN_HTML)){
            httpResponse.forward(responseHeader, TEMPLATES_RESOURCE_PATH_PREFIX + USER_LOGIN_HTML);
        }else if(httpRequest.getPath().endsWith(LOGIN_FAILED_HTML)){
            httpResponse.forward(responseHeader, TEMPLATES_RESOURCE_PATH_PREFIX + USER_LOGIN_FAILED_HTML);
        }else {
            httpResponse.error();
        }
    }

}
