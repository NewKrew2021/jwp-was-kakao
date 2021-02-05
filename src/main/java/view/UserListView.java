package view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import utils.FileIoUtils;
import webserver.Model;
import webserver.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class UserListView implements View {
    private String name = "/user/list";

    @Override
    public boolean canHandle(String name) {
        return name.equals(name);
    }

    @Override
    public void render(Model model, HttpRequest request, HttpResponse httpResponse) {
        try {
            byte[] body = buildPage(request.getUri(), model.getAttributes());
            String mimeType = FileIoUtils.getMimeType("./templates/index.html");
            httpResponse.setBody(body, mimeType);
            httpResponse.setStatus("HTTP/1.1 200 OK");
        } catch (IOException | URISyntaxException e) {
            httpResponse.setStatus("HTTP/1.1 500 Internal Server Error");
        }
    }

    private byte[] buildPage(String path, Map<String, Object> params) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile(path);

        return template.apply(params).getBytes();
    }
}
