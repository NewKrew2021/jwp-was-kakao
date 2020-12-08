package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import model.Users;
import service.UserService;
import webserver.http.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserController extends HttpController {

    public static final String FORM_HTML = "/form.html";
    public static final String USER_CREATE = "/user/create";
    public static final String INDEX_HTML = "/index.html";
    public static final String USER_LOGIN_HTML = "/user/login.html";
    public static final String USER_LOGIN_FAILED_HTML = "/user/login_failed.html";
    public static final String LOGIN_FAILED_HTML = "/login_failed.html";
    public static final String USER_LIST = "/user/list";
    public static final String USER_LIST_HTML = "/user/list.html";
    public static final String LOGIN = "/user/login";
    public static final String TEMPLATES_RESOURCE_PATH_PREFIX = "/templates";

    UserService userService;

    public UserController() {
         userService = new UserService();
    }

    @Override
    public HttpResponse response(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setContentType(httpRequest);
        if(httpRequest.getPath().endsWith(FORM_HTML)){
            return new HttpResponse(responseHeader, setView(httpRequest.getPath()));
        }else if(httpRequest.getPath().endsWith(USER_CREATE)){
            if(httpRequest.getMethod().equals(HttpMethod.GET)){
                QueryString queryString = httpRequest.getBody().getQueryString();
                String userId = queryString.getParameter("userId");
                String password = queryString.getParameter("password");
                String name = queryString.getParameter("name");
                String email = queryString.getParameter("email");
                User user = new User(userId, password, name, email);
                userService.addUser(user);
            }else{
                RequestBody requestBody = httpRequest.getBody();
                String userId = requestBody.getParameter("userId");
                String password = requestBody.getParameter("password");
                String name = requestBody.getParameter("name");
                String email = requestBody.getParameter("email");
                User user = new User(userId, password, name, email);
                userService.addUser(user);
            }
            return new HttpResponse(responseHeader).redirect(INDEX_HTML);
        }else if(httpRequest.getPath().endsWith(LOGIN)){
            RequestBody requestBody = httpRequest.getBody();
            String userId = requestBody.getParameter("userId");
            String password = requestBody.getParameter("password");
            UserService userService = new UserService();
            if(userService.loginProcess(userId, password)){
                responseHeader.setLoginCookie(httpRequest, true);
                return new HttpResponse(responseHeader).redirect(INDEX_HTML);
            }else{
                responseHeader.setLoginCookie(httpRequest, false);
                return new HttpResponse(responseHeader).redirect(USER_LOGIN_FAILED_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LIST_HTML)){
            if(httpRequest.isLogin()){
                UserService userService = new UserService();
                Users users = userService.findAll();
                TemplateLoader loader = new ClassPathTemplateLoader();
                loader.setPrefix(TEMPLATES_RESOURCE_PATH_PREFIX);
                loader.setSuffix(ResponseHeader.HTML);
                Handlebars handlebars = new Handlebars(loader);
                return new HttpResponse(handlebars.compile(USER_LIST).apply(users).getBytes());
            }else{
                return new HttpResponse(responseHeader).redirect(USER_LOGIN_HTML);
            }
        }else if(httpRequest.getPath().endsWith(USER_LOGIN_HTML)){
            return new HttpResponse(responseHeader, setView(USER_LOGIN_HTML));
        }else if(httpRequest.getPath().endsWith(LOGIN_FAILED_HTML)){
            return new HttpResponse(responseHeader, setView(USER_LOGIN_FAILED_HTML));
        }else {
            return new HttpResponse().error();
        }
    }

}
