package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import db.DataBase;
import model.Request;
import model.Response;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.Optional;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public Response mapMethod(Request request) {

        if(request.getPaths()[2].equals("create")){
            return create(request);
        }
        if(request.getPaths()[2].equals("login")){
            return login(request);
        }

        Response response = new Response();
        response.setResponse200Header();
        response.setPath(request.getPath());
        return response;
    }

    private Response create(Request request){
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(request.getParams(), User.class);
        DataBase.addUser(user);
        logger.info("UserInfo: {}, {}, {}, {}", user.getUserId(), user.getEmail(), user.getName(), user.getPassword());

        Response response = new Response();
        response.setResponse302Header("/index.html");
        return response;
    }

    private Response login(Request request){

        ObjectMapper mapper = new ObjectMapper();
        User loginUser = mapper.convertValue(request.getParams(), User.class);
        User user= Optional.ofNullable(DataBase.findUserById(loginUser.getUserId())).orElseThrow(NullPointerException::new);
        Response response=new Response();
        response.setResponse200Header();
        if(user.getPassword().equals(loginUser.getPassword())){
            response.setPath("/index.html");
            response.setCookie("Set-Cookie: logined=true; Path=/\r\n"+"\r\n");
            return response;
        }
        response.setPath("/user/login_failed.html");
        response.setCookie("Set-Cookie: logined=false; Path=/\r\n"+"\r\n");
        return response;
    }

}
