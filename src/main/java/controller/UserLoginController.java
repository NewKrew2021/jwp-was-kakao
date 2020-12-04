package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.SetCookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpResponseBuilder;

import java.util.Map;

public class UserLoginController extends Controller {
    private static final String PATH = "/user/login";

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    protected HttpResponse handlePost(HttpRequest httpRequest) {
        Map<String, String> body = httpRequest.getBodyInMap();
        String userId = body.get("userId");
        String password = body.get("password");

        if (UserService.isLoginSuccessful(userId, password)) {
            return loginSuccess();
        }

        return loginFailed();
    }

    private HttpResponse loginSuccess() {
        SetCookie setCookie = new SetCookie(UserService.LOGINED_KEY, UserService.LOGINED_VALUE);
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
