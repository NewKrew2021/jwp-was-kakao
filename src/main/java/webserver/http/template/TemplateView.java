package webserver.http.template;

import com.google.common.base.Charsets;
import webserver.http.*;

import java.util.Map;

public class TemplateView implements View {

    private TemplateEngine templateEngine;
    private String templatePath;

    public TemplateView(TemplateEngine templateEngine, String templatePath) {
        this.templateEngine = templateEngine;
        this.templatePath = templatePath;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest httpRequest, HttpResponse httpResponse) {
        String output = templateEngine.apply(templatePath, model);
        httpResponse.setStatus(HttpStatus.x200_OK);
        httpResponse.setContentType(MimeType.TEXT_HTML, Charsets.UTF_8);
        httpResponse.setBody(output.getBytes());
        httpResponse.send();
    }
}
