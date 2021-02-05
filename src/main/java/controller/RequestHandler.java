package controller;

import domain.HttpRequest;
import domain.HttpResponse;
import exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Controller defaultController = new FileController();
    private static final List<Controller> controllers = Arrays.asList(
            new UserCreateController(),
            new UserListController(),
            new UserLoginController()
    );

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug(
                "New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort()
        );

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            ExceptionHandler.getInstance().setOutputStream(out);

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            Controller controller = findController(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);

            logger.debug(httpRequest.toString());
            logger.debug(httpResponse.toString());
        } catch (Exception e) {
            ExceptionHandler.getInstance().handle(e);
        }
    }

    private Controller findController(String path) {
        return controllers.stream()
                .filter(controller -> controller.isSupport(path))
                .findFirst()
                .orElse(defaultController);
    }
}
