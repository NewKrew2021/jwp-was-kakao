package webserver;

import db.DataBase;
import dto.HttpRequest;
import model.User;

import java.nio.charset.StandardCharsets;

public class UserController {
    public static byte[] create(HttpRequest request) {
        String userId = request.getQuery().get("userId");
        String password = request.getQuery().get("password");
        String name = request.getQuery().get("name");
        String email = request.getQuery().get("email");

        DataBase.addUser(new User(userId, password, name, email));
        return "성공".getBytes(StandardCharsets.UTF_8);
    }
}
