package controller;

import annotation.web.RequestMethod;
import db.DataBase;
import utils.FileIoUtils;
import utils.TemplateUtils;
import webserver.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends Controller {
    public UserListController() {
        super(RequestMethod.GET, "/user/list");
    }

    @Override
    public boolean canHandle(HttpRequest request) {
        return request.getRequestMethod() == requestMethod && request.getUri().equals(uri);
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) throws IOException, URISyntaxException {
        String logined = request.getRequestHeaders().getHeader("Cookie");

        if ("logined=true".equals(logined)) {
            Map<String, Object> params = new HashMap<>();
            params.put("users", DataBase.findAll());

            return new HttpResponse.Builder()
                    .status("HTTP/1.1 200 OK")
                    .body(TemplateUtils.buildPage(request.getUri(), params),
                            FileIoUtils.getMimeType("./templates/index.html")
                    )
                    .build();
        }
        return new HttpResponse.Builder()
                .status("HTTP/1.1 302 Found")
                .redirect("/user/login.html")
                .build();
    }
}
