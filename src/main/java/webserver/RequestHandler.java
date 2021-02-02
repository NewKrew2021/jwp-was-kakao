package webserver;

import controller.Controller;
import controller.UserController;
import controller.ViewController;
import model.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 3. 에러핸들링도 빈약하고
 */

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    private final List<Controller> controllers = Arrays.asList(new UserController(), new ViewController());

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            List<Controller> selectedControllers = controllers.stream()
                    .filter(controller -> controller.hasSameBasePath(httpRequest.getPath()))
                    .collect(Collectors.toList());
            handleRequest(out, httpRequest, selectedControllers);
        } catch (URISyntaxException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(OutputStream out, HttpRequest httpRequest, List<Controller> controllers)
            throws URISyntaxException, IOException {
        for(Controller controller: controllers){
            if(controller.handle(httpRequest, out)) return;
        }
    }
}
