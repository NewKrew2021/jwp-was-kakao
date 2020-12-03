package apps.slipp.controller;

import com.google.common.base.Charsets;
import apps.slipp.db.DataBase;
import apps.slipp.model.User;
import webserver.http.*;
import webserver.http.template.TemplateEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserListController implements Controller {

    private TemplateEngine templateEngine = TemplateEngine.handlebars();

    public void execute(HttpRequest httpRequest, HttpResponse httpResponse){
        String output = templateEngine.apply(httpRequest.getPath(), getModelData());
        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(MimeType.TEXT_HTML, Charsets.UTF_8);
        httpResponse.setBody(output.getBytes());
    }

    Object getModelData() {
        Collection<User> users = DataBase.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return data;
    }

}
