package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import response.HttpResponseStatusCode;
import utils.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;



public class ListUserController extends AbstractController {

    private static final String LOGIN_PAGE = "/user/login.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.isLogined()) {
            try {
                httpResponse.addResponseBody(makeListUserTemplate());
                httpResponse.send(HttpResponseStatusCode.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        httpResponse.addRedirectionLocationHeader(LOGIN_PAGE);
        httpResponse.addSetCookieHeader(false);
        httpResponse.send(HttpResponseStatusCode.FOUND);
    }

    private byte[] makeListUserTemplate() throws IOException{
        Collection<User> users = DataBase.findAll();
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("user/list");
        String result = template.apply(users);
        return IOUtils.decodeData(result).getBytes(StandardCharsets.UTF_8);
    }

}
