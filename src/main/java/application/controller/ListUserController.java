package application.controller;

import application.user.UserService;
import utils.TemplateUtils;
import webserver.domain.ContentTypes;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;

import java.io.IOException;

public class ListUserController extends AbstractController {

    @Override
    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        if (!httpRequest.isLogined()) {
            httpResponse.response302Found("/user/login.html");
            return;
        }

        try {
            byte[] body = TemplateUtils.makeTemplate(httpRequest.getPath(),UserService.getAllUser());
            httpResponse.response200Ok(body,ContentTypes.HTML.getType());
        } catch (IOException e) {
            e.printStackTrace();
            httpResponse.response404();
        }

    }

}
