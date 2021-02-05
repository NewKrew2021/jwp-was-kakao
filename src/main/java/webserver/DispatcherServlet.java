package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Optional;

public class DispatcherServlet {
    private ControllerMapper controllerMapper = new ControllerMapper();
    private ViewResolver viewResolver = new ViewResolver();

    public void doService(HttpRequest request, HttpResponse response) {
        Optional<Controller> controller = controllerMapper.findController(request);
        if (!controller.isPresent()) {
            return;
        }

        Model model = new Model();
        String viewName = controller.get().handleRequest(request, response, model);
        Optional<View> view = viewResolver.resolveViewName(viewName);

        if (!view.isPresent()) {
            return;
        }
        view.get().render(model, request, response);
    }
}
