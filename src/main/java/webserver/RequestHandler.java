package webserver;

import framework.controller.Controller;
import framework.request.HttpMethod;
import framework.request.HttpRequest;
import framework.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.controller.AbstractController;
import service.controller.CreateUserController;
import service.controller.ListUserController;
import service.controller.LoginController;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.of(in);
            HttpResponse httpResponse = HttpResponse.of(new DataOutputStream(out));

            AbstractController.enroll("/user/list", new ListUserController());
            AbstractController.enroll("/user/create", new CreateUserController());
            AbstractController.enroll("/user/login", new LoginController());

            Controller controller = AbstractController.of(httpRequest.getPath());
            controller.service(httpRequest, httpResponse);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
