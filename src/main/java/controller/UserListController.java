package controller;

import utils.LoginCheckUtils;
import utils.TemplateUtils;
import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserListController extends AbstractController {
    private static final String LOGIN_URL = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!LoginCheckUtils.isLogin(httpRequest.getHeaderValue("Cookie"))) {
            httpResponse.sendRedirect(LOGIN_URL);
            return;
        }

        String html = TemplateUtils.makeTemplate(httpRequest.getPath());
        byte[] body = html.getBytes(StandardCharsets.UTF_8);
        httpResponse.forward(body);
    }
}
