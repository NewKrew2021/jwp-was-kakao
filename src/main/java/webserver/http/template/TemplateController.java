package webserver.http.template;

import webserver.http.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ModelAndView;

import java.util.Map;

public abstract class TemplateController implements Controller {

    protected TemplateEngine templateEngine;

    public TemplateController(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    @Override
    public ModelAndView execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        return ModelAndView.of(getModelData(), getView(httpRequest.getPath()));
    }

    protected TemplateView getView(String viewName) {
        return new TemplateView(templateEngine, viewName);
    }

    protected abstract Map<String, Object> getModelData();
}
