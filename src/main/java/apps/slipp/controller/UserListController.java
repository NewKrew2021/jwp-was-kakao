package apps.slipp.controller;

import apps.slipp.db.DataBase;
import apps.slipp.model.User;
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
    protected Map<String, Object> getModelData() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return data;
    }

}
