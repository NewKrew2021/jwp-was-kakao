package webserver.http.controller;

import db.DataBase;
import model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends HandlebarsController {

    @Override
    protected Object getModelData() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return data;
    }

}
