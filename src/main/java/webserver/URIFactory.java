package webserver;

import dto.ParamValue;
import model.HttpStatus;
import model.User;
import model.factory.UserFactory;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

public class URIFactory {

    private UserService userService = new UserService();

    public Response create(Request reqeust) {
        String urlPath = reqeust.getURLPath();

        if (urlPath.equals("/user/create")) {
            User user = UserFactory.create(reqeust.getParamMap());
            userService.create(user);
            return Response.of(HttpStatus.HTTP_FOUND, ParamValue.of("Location", "/index.html"));

        } else if (urlPath.equals("/user/login")) {
            ParamValue paramMap = reqeust.getParamMap();
            String userId = paramMap.getValue("userId");
            String password = paramMap.getValue("password");

            boolean isLogin = userService.isLogin(userId, password);
            return Response.of(HttpStatus.HTTP_FOUND, parseLoginParamValue(isLogin));
        }

        return Response.of(HttpStatus.HTTP_OK);
    }

    private ParamValue parseLoginParamValue(boolean setCookie) {
        Map<String, String> map = new HashMap();
        map.put("Location", "/index.html");
        map.put("Set-Cookie", "logined=" + setCookie + "; Path=/");
        return new ParamValue(map);
    }
}
