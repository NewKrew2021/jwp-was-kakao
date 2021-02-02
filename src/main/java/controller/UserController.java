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

    public Response mapMethod(Request request) {
        String[] paths = request.getPath().split("/");

        if(paths[2].equals("create")){
            return create(request);
        }
        if(paths[2].equals("login")){
            return login(request);
        }
        if(paths[2].equals("list")){
            return list(request);
        }

        Response response = new Response();
        response.setResponse200Header();
        response.setPath(request.getPath());
        return response;
    }

    private Response create(Request request){
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(request.getParameter(), User.class);
        DataBase.addUser(user);
        logger.info("UserInfo: {}, {}, {}, {}", user.getUserId(), user.getEmail(), user.getName(), user.getPassword());

        Response response = new Response();
        response.setResponse302Header("/index.html");
        return response;
    }

    private Response login(Request request){
        ObjectMapper mapper = new ObjectMapper();
        User loginUser = mapper.convertValue(request.getParameter(), User.class);
        User user= Optional.ofNullable(DataBase.findUserById(loginUser.getUserId())).orElseThrow(NullPointerException::new);
        Response response=new Response();
        if(user.getPassword().equals(loginUser.getPassword())){
            response.setResponse302Header("/index.html");
            response.setCookie("Set-Cookie: logined=true;근 Path=/\r\n"+"\r\n");
            return response;
        }
        response.setResponse302Header("/user/login_failed.html");
        response.setCookie("Set-Cookie: logined=false; Path=/\r\n"+"\r\n");
        return response;
    }

    private Response list(Request request){
        Response response=new Response();
        response.setResponse200Header();
        if(request.isLogin()){
            logger.debug("user/list 로그인된 상태");
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);
            Template template=null;
            try{
                template= handlebars.compile("user/list");
                List<User> users = new ArrayList<>(DataBase.findAll());
                Map<String,Object> map=new HashMap<>();
                map.put("users",users);
                JSONObject json=new JSONObject(map);
                String userList = template.apply(json);
                logger.info("userList : {}", userList);
                response.setBody(userList);
                return response;
            }catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }

        logger.debug("user/list false 상태");
        response.setPath("/user/login.html");
        return response;
    }

}
