package controller;

import annotation.web.RequestMapping;
import annotation.web.RequestMethod;
import annotation.web.RequestParam;
import db.DataBase;
import model.User;

public class RequestController {

    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public void createUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email){
        User user = new User(userId, password, name, email);
        System.out.println(user);
        DataBase.addUser(user);
    }
}
