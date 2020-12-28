package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.*;
import webserver.model.HttpRequest;
import webserver.model.HttpResponse;
import webserver.model.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final Map<String, Controller> controllers = initControllers();
    private static final Controller defaultController = new StaticController();

    private Socket connection;

    private static Map<String, Controller> initControllers() {
        return Stream.of(new UserCreateController(), new UserListController(), new UserLoginController())
                .collect(Collectors.toMap(Controller::getPath, Function.identity()));
    }

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = new HttpResponse(out);
            response.setCookie(HttpSession.SESSION_COOKIE_NAME, request.getSession().getId());

            Controller controller = controllers.getOrDefault(request.getPath(), defaultController);
            controller.handle(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
