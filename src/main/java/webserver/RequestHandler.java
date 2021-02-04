package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.Controller;
import controller.LoginController;
import controller.UserController;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;


public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;
    private final Dispatcher dispatcher = Dispatcher.getInstance();
    private final List<Controller> controllers = new ArrayList<>();
    private final UserController userController = UserController.getInstance();
    private final LoginController loginController = LoginController.getInstance();

    public RequestHandler(Socket connectionSocket) {
        connection = connectionSocket;
        controllers.add(userController);
        controllers.add(loginController);
        controllers.forEach(Controller::registerAll);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = getRequestByInput(in);

            Response response = getResponseByRequest(request);

            printResponse(out, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private Response getResponseByRequest(Request request) throws IOException, URISyntaxException {
        if (FileIoUtils.pathIsFile(request.getUrlPath())) {
            return FileIoUtils.loadFileFromUrlPath(request.getUrlPath());
        }
        return dispatcher.run(request);
    }

    private Request getRequestByInput(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        return new Request(Arrays.stream(IOUtils.readData(br, 5000).split("\r\n"))
                .map(String::trim)
                .collect(Collectors.toList()));
    }

    private void printResponse(OutputStream out, Response response) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        dos.write(response.toBytes(),
                0,
                response.toBytes().length);
        dos.flush();
    }
}
