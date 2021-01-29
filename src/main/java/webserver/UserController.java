package webserver;

import db.DataBase;
import dto.HttpRequest;
import model.User;

import java.nio.charset.StandardCharsets;

public class UserController {
    public static byte[] create(HttpRequest request) {
        String userId = request.getBodyParams().get("userId");
        String password = request.getBodyParams().get("password");
        String name = request.getBodyParams().get("name");
        String email = request.getBodyParams().get("email");

        DataBase.addUser(new User(userId, password, name, email));
        return "성공".getBytes(StandardCharsets.UTF_8);
    }
}
