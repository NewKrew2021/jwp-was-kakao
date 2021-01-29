package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Request;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public String mapMethod(Request request) {

        if(request.getPaths()[2].equals("create")){
            return create(request);
        }
        return request.getPath();
    }

    private String create(Request request){
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(request.getParams(), User.class);
        logger.info("UserInfo: {}, {}, {}, {}", user.getUserId(), user.getEmail(), user.getName(), user.getPassword());
        return "redirect /index.html";
    }

}
