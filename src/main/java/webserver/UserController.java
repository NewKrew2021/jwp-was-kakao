package webserver;

import db.DataBase;
import dto.HttpRequest;
import dto.HttpResponse;
import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public static HttpResponse create(HttpRequest request) {
        String userId = request.getBodyParams().get("userId");
        String password = request.getBodyParams().get("password");
        String name = request.getBodyParams().get("name");
        String email = request.getBodyParams().get("email");

        DataBase.addUser(new User(userId, password, name, email));
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", "http://localhost:8080/index.html");
        return new HttpResponse("HTTP/1.1 302 Found", headers);
    }
}
