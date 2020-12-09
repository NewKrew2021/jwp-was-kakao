package apps.slipp.controller;

import apps.slipp.db.DataBase;
import apps.slipp.model.User;
import webserver.http.HttpRequest;
import webserver.http.session.HttpSession;
import webserver.http.template.TemplateController;
import webserver.http.template.TemplateEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends TemplateController {

    public UserListController(TemplateEngine templateEngine) {
        super(templateEngine);
    }

    @Override
    protected Map<String, Object> getModelData(HttpRequest request) {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();

        HttpSession session = request.getSession();
        Object profile = session.getAttribute("profile");
        if (profile != null)
            data.put("profile", (Profile) profile);
        data.put("users", users);
        return data;
    }

}
