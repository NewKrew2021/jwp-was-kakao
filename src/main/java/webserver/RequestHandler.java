package webserver;

import controller.*;
import domain.HttpRequest;
import domain.HttpResponse;
import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

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

        ExceptionHandler exceptionHandler = null;
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            exceptionHandler = new ExceptionHandler(out);

            HttpRequest httpRequest = new HttpRequest(in);
            Controller controller = findController(httpRequest.getPath());
            HttpResponse httpResponse = controller.service(httpRequest);
            httpResponse.send(out);

            logger.debug(httpRequest.toString());
            logger.debug(httpResponse.toString());
        } catch (HttpException e) {
            e.printStackTrace();
            exceptionHandler.handle(e);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionHandler.handle(new HttpException(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private Controller findController(String path) {
        return controllers.stream()
                .filter(controller -> controller.isSupport(path))
                .findFirst()
                .orElse(defaultController);
    }
}
