package controller;

import utils.TemplateUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserListController extends AbstractController {
    private static final String LOGIN_URL = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!isLogin(httpRequest.getHeader("Cookie"))) {
            httpResponse.sendRedirect(LOGIN_URL);
            return;
        }

        String html = TemplateUtils.makeTemplate(httpRequest.getPath());
        byte[] body = html.getBytes(StandardCharsets.UTF_8);
        httpResponse.forward(body);
    }

    private boolean isLogin(String loginCookie) {
        try {
            return Boolean.parseBoolean(loginCookie.split("=")[1]);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
