package controller;

import controller.error.ErrorHandlerFactory;
import controller.handler.Handler;
import exception.utils.NoFileException;
import model.request.HttpRequest;
import model.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerManager {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final List<Controller> controllers = new ArrayList<>();
    static {
        registerController(new ViewController());
        registerController(new UserController());
    }

    public static void registerController(Controller controller) {
        controllers.add(controller);
    }

    public static void runControllers(Socket connection) {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(out);
            handleRequest(httpRequest, httpResponse);
        } catch (IOException e) {
            log.error("{} {}", e.getMessage(), e.getStackTrace());
        }
    }

    private static void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        List<Controller> selectedControllers = controllers.stream()
                .filter(controller -> controller.hasSameBasePath(httpRequest.getPath()))
                .collect(Collectors.toList());
        try {
            for (Controller controller : selectedControllers) {
                if (controller.handle(httpRequest, httpResponse)) return;
            }

            defaultHandle(httpRequest, httpResponse);
        } catch (NoFileException | IOException | RuntimeException e) {
            Handler errorHandler = ErrorHandlerFactory.getHandler(e);
            try {
                errorHandler.handle(httpRequest, httpResponse);
                log.warn(e.getMessage());
            } catch (Exception handleError) {
                log.error("{} {}", handleError.getMessage(), handleError.getStackTrace());
            }
        }
    }

    public static void defaultHandle(HttpRequest request, HttpResponse response) throws NoFileException {
        response.forward("./templates", request.getPath());
    }
}
