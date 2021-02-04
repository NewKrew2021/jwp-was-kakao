package controller;

import controller.error.ErrorHandlerFactory;
import controller.handler.ErrorHandler;
import exception.http.CannotHandleException;
import exception.utils.NoFileException;
import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.DataOutputStream;
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
            HttpRequest httpRequest = new HttpRequest(in, connection);
            List<Controller> selectedControllers = controllers.stream()
                    .filter(controller -> controller.hasSameBasePath(httpRequest.getPath()))
                    .collect(Collectors.toList());
            HttpResponse response = handleRequest(httpRequest, selectedControllers);
            response.ok(new DataOutputStream(out));
            log.info("{} {}", httpRequest.getRemoteAddress(), response.getStartLine());
        } catch (IOException e) {
            log.error("{} {}", e.getMessage(), e.getStackTrace());
        }
    }

    private static HttpResponse handleRequest(HttpRequest httpRequest, List<Controller> controllers) {
        try {
            return tryControllers(httpRequest, controllers);
        } catch (NoFileException | IOException | RuntimeException e) {
            return handleError(httpRequest, e);
        }
    }

    private static HttpResponse handleError(HttpRequest httpRequest, Exception e) {
        ErrorHandler errorHandler = ErrorHandlerFactory.getHandler(e);
        return errorHandler.handle(httpRequest);
    }

    private static HttpResponse tryControllers(HttpRequest httpRequest, List<Controller> controllers)
            throws NoFileException, IOException {
        Iterator<Controller> iter = controllers.iterator();
        HttpResponse response = null;

        while (iter.hasNext() && response == null) {
            Controller controller = iter.next();
            response = controller.handle(httpRequest);
        }

        if (response == null) {
            throw new CannotHandleException();
        }
        return response;
    }
}