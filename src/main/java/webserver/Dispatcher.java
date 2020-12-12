package webserver;

import controller.Controller;
import model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.ResourceResolver;
import webserver.request.HttpRequest;
import webserver.requestmapping.RequestMapping;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.lang.reflect.Method;

import static context.ApplicationContext.viewResolver;

public class Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public void dispatch(HttpRequest request, DataOutputStream out) {
        try {
            HttpResponse response = HttpResponse.ok(request);
            dispatch(request, response);
            response.write(out);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void dispatch(HttpRequest request, HttpResponse response) {
        Method method = findControllerMethod(request);
        Model model = Model.empty();
        if (method == null) {
            dispatchResource(request, response);
            return;
        }
        dispatchWithHandler(request, response, method, model);
    }

    private Method findControllerMethod(HttpRequest request) {
        return RequestMapping.findMethod(request.getPath(), request.getMethod());
    }

    private void dispatchResource(HttpRequest request, HttpResponse response) {
        dispatchView(response, ResourceResolver.resolve(request.getPath()));
    }

    private void dispatchView(HttpResponse response, String viewPath) {
        dispatchModelAndView(response, null, viewPath);
    }

    private void dispatchModelAndView(HttpResponse response, Model model, String viewPath) {
        if (viewPath != null) {
            response.setView(viewResolver.resolve(model, viewPath));
        }
    }

    private void dispatchWithHandler(HttpRequest request, HttpResponse response, Method method, Model model) {
        if (method.getParameterCount() == 2) {
            invokeMethod(request, response, method);
        }
        if (method.getParameterCount() == 3) {
            String viewPath = invokeMethod(request, method, response, model);
            dispatchModelAndView(response, model, viewPath);
        }
    }

    private void invokeMethod(HttpRequest request, HttpResponse response, Method method) {
        try {
            method.invoke(getController(request), request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Controller getController(HttpRequest request) {
        return RequestMapping.findController(request.getPath());
    }

    private String invokeMethod(HttpRequest request, Method method, HttpResponse response, Model model) {
        try {
            return (String) method.invoke(getController(request), request, response, model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
