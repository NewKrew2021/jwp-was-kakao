package controller;

import controller.error.ErrorHandlerFactory;
import controller.handler.Handler;
import exception.utils.NoFileException;
import model.HttpRequest;
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

    public static void registerController(Controller controller) {
        controllers.add(controller);
    }

    public static void runControllers(Socket connection) {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            List<Controller> selectedControllers = controllers.stream()
                    .filter(controller -> controller.hasSameBasePath(httpRequest.getPath()))
                    .collect(Collectors.toList());
            handleRequest(out, httpRequest, selectedControllers);
        } catch (IOException e) {
            log.error("{} {}", e.getMessage(), e.getStackTrace());
        }
    }

    private static void handleRequest(OutputStream out, HttpRequest httpRequest, List<Controller> controllers) {
        try {
            for (Controller controller : controllers) {
                if (controller.handle(httpRequest, out)) return;
            }
        } catch (NoFileException | IOException | RuntimeException e) {
            Handler errorHandler = ErrorHandlerFactory.getHandler(e);
            try {
                errorHandler.handle(httpRequest, out);
                log.warn(e.getMessage());
            } catch (Exception handleError) {
                log.error("{} {}", handleError.getMessage(), handleError.getStackTrace());
            }
        }
    }
}
