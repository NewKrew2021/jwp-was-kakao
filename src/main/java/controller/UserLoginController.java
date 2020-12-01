package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.SetCookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseBuilder;

import java.util.HashMap;
import java.util.Map;

public class UserLoginController extends Controller {
    public static final String PATH = "/user/login";

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Override
    protected HttpResponse handlePost(HttpRequest httpRequest) {
        Map<String, String> body = new HashMap<>();
        httpRequest.getBodyInMap(body);
        String userId = body.get("userId");
        String password = body.get("password");

        if (UserService.isLoginSuccessful(userId, password)) {
            return loginSuccess();
        }

        return loginFailed();
    }

    private HttpResponse loginSuccess() {
        SetCookie setCookie = new SetCookie("logined", "true");
        setCookie.setPath("/");

        return new HttpResponseBuilder()
                .with302Redirect("/index.html")
                .withSetCookie(setCookie)
                .build();
    }

    private HttpResponse loginFailed() {
        return new HttpResponseBuilder()
                .with302Redirect("/user/login_failed.html")
                .build();
    }

}
