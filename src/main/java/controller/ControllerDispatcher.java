package controller;

import controller.error.ErrorHandlerFactory;
import controller.handler.Handler;
import exception.http.CannotHandleException;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final List<Controller> controllers = new ArrayList<>();

    public static void registerController(Controller controller) {
        controllers.add(controller);
    }

    public static void dispatch(Socket connection) {
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
            HttpResponse response = tryControllers(out, httpRequest, controllers);
            response.ok();
            log.info("{}", response.getStartLine());
        } catch (NoFileException | IOException | RuntimeException e) {
            handleError(out, httpRequest, e);
        }
    }

    private static void handleError(OutputStream out, HttpRequest httpRequest, Exception e) {
        Handler errorHandler = ErrorHandlerFactory.getHandler(e);
        try {
            HttpResponse response = errorHandler.handle(httpRequest, out);
            response.ok();
            log.warn(e.getMessage());
        } catch (Exception handleError) {
            log.error("{} {}", handleError.getMessage(), handleError.getStackTrace());
        }
    }

    private static HttpResponse tryControllers(OutputStream out, HttpRequest httpRequest, List<Controller> controllers)
            throws NoFileException, IOException {
        Iterator<Controller> iter = controllers.iterator();
        HttpResponse response = null;

        while (iter.hasNext() && response == null) {
            Controller controller = iter.next();
            response = controller.handle(httpRequest, out);
        }

        if (response == null) {
            throw new CannotHandleException();
        }
        return response;
    }
}
