package service.controller;

import framework.controller.Controller;
import framework.request.HttpMethod;
import framework.request.HttpRequest;
import framework.response.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController implements Controller {

    private static final Map<String, Controller> controllers = new HashMap<>();

    public static void enroll(String path, Controller controller) {
        controllers.put(path, controller);
    }

    public static Controller of(String path) {
        Controller controller = controllers.get(path);
        return (controller == null) ? new FileController() : controller;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.getMethod().equals(HttpMethod.GET)) {
            doGet(request, response);
        }
        if (request.getMethod().equals(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        response.badRequest();
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.badRequest();
    }

}
