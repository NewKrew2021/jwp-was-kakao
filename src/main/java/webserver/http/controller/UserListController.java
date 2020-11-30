package webserver.http.controller;

import db.DataBase;
import model.User;
import utils.ClasspathFileLoader;
import utils.FileLoader;
import webserver.http.*;
import webserver.http.template.TemplateEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController implements Controller {

    private TemplateEngine templateEngine = TemplateEngine.handlebars();
    private FileLoader fileLoader = new ClasspathFileLoader("./templates");

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        Collection<User> users = DataBase.findAll();

        byte[] templateContent = fileLoader.load(httpRequest.getPath() + ".html");
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        String output = templateEngine.apply(new String(templateContent), data);

        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
        httpResponse.setBody(output.getBytes());
    }

}
