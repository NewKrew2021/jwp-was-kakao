package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import exceptions.MethodNotAllowedException;
import model.Request;
import model.Response;
import model.User;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);


    @Override
    public void service(Request request, Response response) {
        super.service(request, response);
    }

    @Override
    void doPost(Request request, Response response) {
        throw new MethodNotAllowedException();
    }

    @Override
    void doGet(Request request, Response response) {
        if (request.isLogin()) {
            try {
                logger.debug("user/list 로그인된 상태");

                List<User> users = new ArrayList<>(DataBase.findAll());
                Map<String, Object> map = new HashMap<>();
                map.put("users", users);

                response.forwardBody(FileIoUtils.loadTemplateWithHandlebars("user/list", map));
                return;
            } catch (Exception e) {
                logger.debug(e.getMessage());
            }
        }
        logger.debug("user/list false 상태");
        response.sendRedirect("/user/login.html");
    }
}
