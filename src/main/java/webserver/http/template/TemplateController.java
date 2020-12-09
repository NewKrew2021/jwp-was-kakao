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
    public ModelAndView execute(HttpRequest request, HttpResponse response) {
        return ModelAndView.of(getModelData(request), getView(request.getPath()));
    }

    protected TemplateView getView(String viewName) {
        return new TemplateView(templateEngine, viewName);
    }

    protected abstract Map<String, Object> getModelData(HttpRequest request);

}
