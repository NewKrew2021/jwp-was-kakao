package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    public static HttpResponse create(HttpRequest request) {
        String userId = request.getBodyParams().get("userId");
        String password = request.getBodyParams().get("password");
        String name = null;
        String email = null;
        try {
            name = URLDecoder.decode(request.getBodyParams().get("name"), "UTF-8");
            email = URLDecoder.decode(request.getBodyParams().get("email"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        DataBase.addUser(new User(userId, password, name, email));
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "http://localhost:8080/index.html");
        return new HttpResponse("HTTP/1.1 302 Found", headers);
    }

    public static HttpResponse login(HttpRequest httprequest) {
        User user = DataBase.findUserById(httprequest.getBodyParams().get("userId"));
        Map<String, String> headers = new HashMap<>();
        if(user != null && user.getPassword().equals(httprequest.getBodyParams().get("password"))) {
            headers.put("Location", "http://localhost:8080/index.html");
            headers.put("Set-Cookie", "logined=true; Path=/");
            return new HttpResponse("HTTP/1.1 302 Found", headers);
        }

        headers.put("Location", "http://localhost:8080/user/login_failed.html");
        headers.put("Set-Cookie", "logined=false; Path=/");
        return new HttpResponse("HTTP/1.1 302 Found", headers);
    }

    public static HttpResponse getUsers(HttpRequest request) throws Exception {
        if(request.getCookie().equals("logined=true")) {

            Map<String, Object> model = new HashMap<>();

            List<User> users = new ArrayList<>(DataBase.findAll());

            model.put("users", users);

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/list");

            String userListPage = template.apply(model);
            return new HttpResponse("HTTP/1.1 200 OK", userListPage.getBytes());
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "http://localhost:8080/user/login.html");
        return new HttpResponse("HTTP/1.1 302 Found", headers);
    }
}
