package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.Request;
import model.Response;
import model.User;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.*;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public void mapMethod(Request request, Response response) {
        String[] paths = request.getPath().split("/");

        if(paths[2].equals("create")){
            create(request, response);
            return;
        }
        if(paths[2].equals("login")){
            login(request, response);
            return;
        }
        if(paths[2].equals("list")){
            list(request,response);
            return;
        }
        response.forward(request.getPath());
        return;
    }

    private void create(Request request, Response response){
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(request.getAllParameter(), User.class);
        DataBase.addUser(user);
        logger.info("UserInfo: {}, {}, {}, {}", user.getUserId(), user.getEmail(), user.getName(), user.getPassword());
        response.sendRedirect("/index.html");
    }

    private void login(Request request, Response response){
        ObjectMapper mapper = new ObjectMapper();
        User loginUser = mapper.convertValue(request.getAllParameter(), User.class);
        User user= Optional.ofNullable(DataBase.findUserById(loginUser.getUserId())).orElseThrow(NullPointerException::new);
        if(user.getPassword().equals(loginUser.getPassword())){
            response.addHeader("Set-Cookie", "logined=true; Path=/");
            response.sendRedirect("/index.html");
            return;
        }
        response.addHeader("Set-Cookie", "logined=false;");
        response.sendRedirect("/user/login_faild.html");
    }

    private void list(Request request, Response response){
        if(request.isLogin()){
            logger.debug("user/list 로그인된 상태");
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template = null;
            try{
                template= handlebars.compile("user/list");
                List<User> users = new ArrayList<>(DataBase.findAll());
                Map<String,Object> map=new HashMap<>();
                map.put("users",users);
                JSONObject json=new JSONObject(map);
                String userList = template.apply(json);
                logger.info("userList : {}", userList);
                response.forwardBody(userList);
                return;
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        logger.debug("user/list false 상태");
        response.sendRedirect("/user/login.html");
        return;
    }

}
