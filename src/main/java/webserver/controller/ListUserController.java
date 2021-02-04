package webserver.controller;

import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.HttpHeader;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatusCode;

import java.util.ArrayList;
import java.util.HashMap;

public class ListUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        if (!isLogin(httpRequest.getHeader(HttpHeader.COOKIE))) {
            return new HttpResponse.Builder()
                    .status(HttpStatusCode.FOUND)
                    .redirect("/user/login.html")
                    .build();
        }
        HashMap<String,Object> users = new HashMap<>();
        users.put("users",new ArrayList<>(DataBase.findAll()));
        return new HttpResponse.Builder()
                .status(HttpStatusCode.OK)
                .contentType("text/html;charset=utf-8")
                .body(httpRequest.getPath(),users)
                .build();

    }

    private boolean isLogin(String loginCookie) {
        return loginCookie.contains("logined=true");
    }
}
